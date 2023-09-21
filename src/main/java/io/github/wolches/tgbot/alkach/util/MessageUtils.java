package io.github.wolches.tgbot.alkach.util;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageUtils {

    public static boolean hasText(Message message) {
        return  message.getText() != null &&
                !message.getText().equals("");
    }

    public static boolean isCommand(Message message) {
        return message.getText().startsWith("/");
    }
}
