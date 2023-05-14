package io.github.wolches.tgbot.alkach.bot.contract.authorization;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class AuthorizationAspect { //TODO Finish

    private final Map<Thread, Map<Class<PartialBotApiMethod>, Integer>> restrictions = new ConcurrentHashMap<>();

    @Around("@annotation(AuthorizationRequired)")
    public Object authorizationRequired(ProceedingJoinPoint joinPoint) throws Throwable {
        // TODO
        return joinPoint.proceed();
    }

    @Around(value = "@annotation(Authorized)", argNames = "types, maxCalls")
    public Object authorized(ProceedingJoinPoint joinPoint) throws Throwable {
        Thread thread = Thread.currentThread();
        // TODO
        return joinPoint.proceed();
    }


}
