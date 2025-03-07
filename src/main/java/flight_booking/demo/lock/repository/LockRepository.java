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
            if(lock.tryLock(retry, timeout, TimeUnit.SECONDS)){
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
                lock.unlock();
                return true;
            }
        } catch (IllegalMonitorStateException e) {
            return false;
        }
        return false;
    }
}
