package io.github.wolches.tgbot.alkach.service.message;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageProcessingService {

    default boolean isApplicable(Message message) {
        return  message != null &&
                message.getText() != null &&
                !message.getText().equals("") &&
                checkIsNotCommand(message) &&
                isApplicableInternal(message);
    }

    default boolean isApplicableInternal(Message message) {
        return false;
    }

    default String processMessage(Message message) {
        return isApplicable(message) ? processMessageInternal(message) : null;
    }

    String processMessageInternal(Message message);

    default boolean checkIsNotCommand(Message message) {
        return !message.getText().startsWith("/");
    }
}
