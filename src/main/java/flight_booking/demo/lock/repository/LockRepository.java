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
    public Boolean lock(String key, int retry, int timeout) {
        RLock lock = redissonClient.getLock(key);

        try {
            log.info("try to acquire LOCK: {}", Thread.currentThread().getId());
            System.out.println("try to acquire LOCK: " + Thread.currentThread().getId());
            if(lock.tryLock(retry, timeout, TimeUnit.SECONDS)){
                log.info("lock acquired by Thread: {}", Thread.currentThread().getId());
                return true;
            }
            return false;
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public Boolean unlock(String key) {
        RLock lock = redissonClient.getLock(key);

        try {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                log.info("unlock: {}", Thread.currentThread().getId());
                lock.unlock();
                return true;
            }
        } catch (IllegalMonitorStateException e) {
            return false;
        }
        return false;
    }
}
