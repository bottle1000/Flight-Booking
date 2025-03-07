package flight_booking.demo.lock.ticketlock;

import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.common.exception.ServerErrorResponseCode;
import flight_booking.demo.domain.order.entity.Order;
import flight_booking.demo.lock.repository.LockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
@org.springframework.core.annotation.Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class TicketLockAspect {
    private final LockRepository lockRepository;

    @Around("@annotation(TicketLock)")
    public Object applyLock(ProceedingJoinPoint joinPoint) throws Throwable {
        String prefix = "ticket_lock:";

        Object[] args = joinPoint.getArgs();
        Order order = Arrays.stream(args).filter(argument -> argument instanceof Order).findFirst()
                .map(argument -> (Order) argument)
                .orElseThrow(IllegalArgumentException::new);

        try {
            order.getTicketIds().forEach(ticketId -> {
                if (!lockRepository.lock(prefix + ticketId, 0, 5)) {
                    throw new CustomException(ServerErrorResponseCode.LOCK_CONFLICT);
                }
                log.info("Ticket Lock for [ ticket_lock:{} ]", ticketId);
            });
            
            log.info("Process JoinPoint");
            return joinPoint.proceed();
        } finally {
            order.getTicketIds().forEach(ticketId -> {
                lockRepository.unlock(prefix + ticketId);
                log.info("Ticket Unlock for [ ticket_lock:{} ]", ticketId);
            });
        }
    }
}
