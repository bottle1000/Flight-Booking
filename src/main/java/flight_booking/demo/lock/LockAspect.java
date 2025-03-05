package flight_booking.demo.lock;

import flight_booking.demo.common.exception.CustomException;
import flight_booking.demo.common.exception.ServerErrorResponseCode;
import flight_booking.demo.lock.repository.LockRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.Ordered;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class LockAspect {
    private final LockRepository lockRepository;
    private final ExpressionParser parser = new SpelExpressionParser();
    private final ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();


    @Around("@annotation(Lock)")
    public Object applyLock(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Lock lockAnnotation = signature.getMethod().getAnnotation(Lock.class);
        if(!isValidExpressions(lockAnnotation))
            throw new IllegalStateException("Lock retry 혹은 timeout 설정은 음수가 될 수 없습니다.");

        Long lockKeyValue = extractLockKey(signature, joinPoint.getArgs(), lockAnnotation.key());

        String key = lockAnnotation.prefix() + lockKeyValue;
        int retry = parseIntegerSetting(lockAnnotation.retry());
        int timeout = parseIntegerSetting(lockAnnotation.timeout());

        if (!lockRepository.lock(key, retry, timeout)) {
            throw new CustomException(ServerErrorResponseCode.LOCK_CONFLICT);
        }

        try {
            return joinPoint.proceed();
        } finally {
            lockRepository.unlock(key);
        }
    }

    private Long extractLockKey(MethodSignature signature, Object[] args, String keyExpression) {
        if (keyExpression == null || keyExpression.trim().isEmpty()) {
            throw new IllegalArgumentException("Lock key 를 입력하여 주십시오.");
        }
        try {
            Expression expression = parser.parseExpression(keyExpression);
            EvaluationContext context = new StandardEvaluationContext();

            String[] paramNames = discoverer.getParameterNames(signature.getMethod());
            if (paramNames == null) {
                throw new IllegalStateException("해당 메소드: " + signature.getMethod().getName() + " 에 Key 값으로 추측되는 인자가 존재하지 않습니다.");
            }

            for (int i = 0; i < args.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }

            Object key = expression.getValue(context);

            if (key == null) {
                throw new IllegalArgumentException("Lock key 는 null 값이 될 수 없습니다. 입력된 Key 값: " + keyExpression);}
            if (!(key instanceof Number)) {
                throw new IllegalArgumentException("Lock key 에는 Long 타입이 강제됩니다. 입력된 Key 값: " + keyExpression);}

            return ((Number) key).longValue();
        } catch (Exception e) {
            throw new IllegalArgumentException("Lock key 생성에 실패하였습니다. 입력된 Key 값: " + keyExpression, e);
        }
    }

    private boolean isValidExpressions(Lock annotation) {
        return parseIntegerSetting(annotation.retry()) >= 0 && parseIntegerSetting(annotation.timeout()) >= 0;
    }

    private int parseIntegerSetting(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
