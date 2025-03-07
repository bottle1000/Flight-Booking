package flight_booking.demo.lock;

import flight_booking.demo.BaseTest;
import flight_booking.demo.common.exception.ServerErrorResponseCode;
import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.domain.order.entity.Order;
import flight_booking.demo.lock.repository.LockRepository;
import flight_booking.demo.lock.ticketlock.TicketLockAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

public class TicketLockTest extends BaseTest {
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private TicketLockAspect ticketLockAspect;
    @Autowired
    private LockRepository lockRepository;

    @Test
    public void MultipleLockSuccess() throws Throwable {
        Order order = Mockito.mock(Order.class);
        List<Long> ticketIds = Arrays.asList(1L, 2L, 3L);
        Mockito.when(order.getTicketIds()).thenReturn(ticketIds);

        ProceedingJoinPoint joinPoint = Mockito.mock(ProceedingJoinPoint.class);
        Mockito.when(joinPoint.getArgs()).thenReturn(new Object[]{order});

        // CountDownLatch 를 사용해 proceed()가 완료되지 않고 블록되도록 함
        CountDownLatch latch = new CountDownLatch(1);
        // proceed() 호출 시 latch 가 해제될 때까지 대기하도록 함
        Object proceedResult = new Object();
        Mockito.when(joinPoint.proceed()).thenAnswer(invocation -> {
            latch.await(); // 락 상태를 확인하는 동안 대기
            return proceedResult;
        });
        // 별도 스레드에서 TicketLockAspect.applyLock() 호출
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Object> future = executor.submit(() -> {
            try {
                return ticketLockAspect.applyLock(joinPoint);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });

        List<String> keys = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            keys.clear();
            Iterable<String> keysIterable = redissonClient.getKeys().getKeysByPattern("ticket_lock:*");
            keysIterable.forEach(keys::add);
            if (keys.size() == 3) {
                break;
            }
            Thread.sleep(100);
        }
        assertEquals(3, keys.size(), "Expected 3 lock keys in Redisson");

        latch.countDown();

        Object result = future.get(5, TimeUnit.SECONDS);
        assertEquals(proceedResult, result, "Proceed result should match");

        List<String> keysAfter = new ArrayList<>();
        Iterable<String> keysAfterIterable = redissonClient.getKeys().getKeysByPattern("ticket_lock:*");
        keysAfterIterable.forEach(keysAfter::add);
        assertEquals(0, keysAfter.size(), "Expected no lock keys after unlocking");

        executor.shutdown();
    }

    @Test
    public void testLockRepositoryIntegrationFailure() throws Throwable {
        Order order = Mockito.mock(Order.class);
        List<Long> ticketIds = Arrays.asList(1L, 2L, 3L);
        Mockito.when(order.getTicketIds()).thenReturn(ticketIds);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> futureLock = executor.submit(() -> {
            RLock lock = redissonClient.getLock("ticket_lock:3");
            lock.lock();
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        });
        Thread.sleep(1000);

        ProceedingJoinPoint joinPoint = Mockito.mock(ProceedingJoinPoint.class);
        Mockito.when(joinPoint.getArgs()).thenReturn(new Object[]{order});
        Object proceedResult = new Object();
        Mockito.when(joinPoint.proceed()).thenReturn(proceedResult);

        // 메인 스레드에서 TicketLockAspect를 호출하면,
        // "ticket_lock:1"에 대해 이미 락이 선점되어 있으므로 CustomException이 발생해야 함
        CustomException lockException = assertThrows(CustomException.class, () -> ticketLockAspect.applyLock(joinPoint));
        assertEquals(ServerErrorResponseCode.LOCK_CONFLICT, lockException.getServerErrorResponseCode());
        // Executor 정리
        executor.shutdownNow();
    }
}