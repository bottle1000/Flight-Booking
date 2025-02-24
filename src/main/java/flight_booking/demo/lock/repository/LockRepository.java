package flight_booking.demo.lock.repository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LockRepository {

    private final RedissonClient redissonClient;

    private String generateLockKey(int key) {
        return "lock:" + key;
    }

    public Boolean lock(int key) {
        String lockKey = generateLockKey(key);
        RLock lock = redissonClient.getLock(lockKey);

        try {
            log.info("TRY to get LOCK: {}", Thread.currentThread().getId());
            return lock.tryLock(3, 5, TimeUnit.SECONDS);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public Boolean unlock(int key) {
        String lockKey = generateLockKey(key);
        RLock lock = redissonClient.getLock(lockKey);

        try {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                log.info("UNLOCK this lock: {}", Thread.currentThread().getId());
                lock.unlock();
                return true;
            }
        } catch (IllegalMonitorStateException e) {
            return false;
        }
        return false;
    }
}
