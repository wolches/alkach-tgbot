package io.github.wolches.tgbot.alkach.authorization;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class AuthorizationAspect {

    private final Map<Thread, Map<Class<? extends PartialBotApiMethod>, Integer>> restrictions = new ConcurrentHashMap<>();

    @Around("@annotation(AuthorizationRequired)")
    public Object authorizationRequired(ProceedingJoinPoint joinPoint) throws Throwable {
        Map<Class<? extends PartialBotApiMethod>, Integer> types = restrictions.get(Thread.currentThread());
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof PartialBotApiMethod) {

                Class<PartialBotApiMethod> clazz = (Class<PartialBotApiMethod>) arg.getClass();
                Integer count = types.get(clazz);
                if (count != null && count > 0) {
                    throw new IllegalAccessException("Restricted bot API method");
                }

                count -= 1;
                types.put(clazz, count);
            }
        }
        return joinPoint.proceed();
    }

    @Around(value = "@annotation(Authorized)", argNames = "types, maxCalls")
    public Object authorized(ProceedingJoinPoint joinPoint) throws Throwable {
        Authorized annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Authorized.class);
        Class<PartialBotApiMethod>[] types = annotation.types();
        int[] maxCalls = annotation.maxCalls();

        assert types.length == maxCalls.length;

        HashMap<Class<? extends PartialBotApiMethod>, Integer> allowedCalls = new HashMap<>();
        for (int i = 0; i < types.length; i++) {
            allowedCalls.put(types[i], maxCalls[i]);
        }

        Thread thread = Thread.currentThread();
        restrictions.put(thread, allowedCalls);

        Object proceed = joinPoint.proceed();
        restrictions.remove(thread);

        return proceed;
    }


}
