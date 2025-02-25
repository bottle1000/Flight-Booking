package flight_booking.demo.lock.service;

import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.lock.repository.LockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.function.Supplier;

import static flight_booking.demo.common.exception.ResponseCode.LOCK_CONFLICT;

@Service
@RequiredArgsConstructor
@Slf4j
public class LockService {
    private final LockRepository lockRepository;

    public <T> T withLock(int key, Supplier<T> logic) {
        if (!lockRepository.lock(key)) {
            throw new CustomException(LOCK_CONFLICT);
        }

        try {
            return logic.get();
        } finally {
            lockRepository.unlock(key);
        }
    }
}
