package flight_booking.demo.lock;

import com.zaxxer.hikari.HikariDataSource;
import flight_booking.demo.BaseTest;
import flight_booking.demo.common.exception.CustomException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LockTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(LockTest.class);

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private LockTestService lockTestService;
    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void cleanup() {
        // 테스트 간 Redis 데이터 초기화
        redissonClient.getKeys().flushall();
    }

    @Test
    void 동시에_100개의_요청이_들어올때_단일_스레드만_락획득() throws Exception {
        // Arrange
        final int THREAD_COUNT = 10;
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        Long ticketId = 1L;

        // Act
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                try {
                    latch.countDown();
                    latch.await();
                    lockTestService.processWithLock(ticketId);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        // Assert
        executor.shutdown();
        if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
            log.warn("Not all threads finished execution");
        }
        assertThat(lockTestService.getProcessCount()).isEqualTo(1);
    }

    @Test
    void 락_획득_실패시_LOCK_CONFLICT_예외_발생() throws InterruptedException {
        Long ticketId = 2L;
        CountDownLatch lockAcquiredLatch = new CountDownLatch(1);
        CountDownLatch testCompletedLatch = new CountDownLatch(1);

        Thread lockHolderThread = new Thread(() -> {
            RLock lock = redissonClient.getLock("default_lock:" + ticketId);
            lock.lock();
            try {
                log.info("락 획득 - 스레드: {}", Thread.currentThread().getId());
                lockAcquiredLatch.countDown();
                testCompletedLatch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
                log.info("락 해제 - 스레드: {}", Thread.currentThread().getId());
            }
        });

        lockHolderThread.start();
        lockAcquiredLatch.await();

        // Act & Assert
        assertThatThrownBy(() -> lockTestService.processWithLock(ticketId))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining("Lock 획득을 시도하였으나 실패하였습니다.");

        testCompletedLatch.countDown();
        lockHolderThread.join();
    }


    @TestConfiguration
    static class TestConfig {
        @Bean
        public LockTestService lockTestService() {
            return new LockTestService();
        }
    }

    static class LockTestService {
        private int processCount = 0;

        @Lock(key = "#ticketId", retry="1", timeout ="3")
        public void processWithLock(Long ticketId) {
            processCount++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        }

        public int getProcessCount() {
            return processCount;
        }
    }
}
