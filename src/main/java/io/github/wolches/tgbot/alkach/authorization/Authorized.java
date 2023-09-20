package io.github.wolches.tgbot.alkach.authorization;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorized {

    Class<PartialBotApiMethod>[] types();

    int[] maxCalls();
}
