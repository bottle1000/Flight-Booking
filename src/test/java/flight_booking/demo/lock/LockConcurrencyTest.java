package flight_booking.demo.lock;

import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.lock.service.LockService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.*;

import static flight_booking.demo.common.exception.ServerErrorResponseCode.LOCK_CONFLICT;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LockConcurrencyTest {

    @Autowired
    LockService lockService;

    @Autowired
    RedissonClient redissonClient;

    @Test
    @DisplayName("동시에 여러 쓰레드가 같은 락을 요청하면 하나만 락을 얻고 나머지는 실패해야 한다")
    void concurrentLockTest() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        Set<Long> successThreads = Collections.newSetFromMap(new ConcurrentHashMap<>());
        int lockKey = 12345;

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    lockService.withLock(lockKey, () -> {
                        successThreads.add(Thread.currentThread().getId());
                        sleepSafely(10000);
                        return null;
                    });
                } catch (CustomException e) {
                    assertEquals(LOCK_CONFLICT, e.getServerErrorResponseCode());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        assertEquals(1, successThreads.size(), "하나의 쓰레드만 락 획득에 성공해야 한다.");
        System.out.println("Lock 획득 성공한 Thread ID : " + successThreads);
    }

    private void sleepSafely(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}