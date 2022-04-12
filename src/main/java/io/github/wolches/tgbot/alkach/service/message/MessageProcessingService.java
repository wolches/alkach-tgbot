package io.github.wolches.tgbot.alkach.service.message;

import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageProcessingService {

    default boolean isApplicable(Message message, Chat chat, ChatUser user) {
        return  message != null &&
                message.getText() != null &&
                !message.getText().equals("") &&
                checkIsNotCommand(message) &&
                isApplicableInternal(message, chat, user);
    }

    default boolean isApplicableInternal(Message message, Chat chat, ChatUser user) {
        return false;
    }

    default String processMessage(Message message, Chat chat, ChatUser user) {
        return isApplicable(message, chat, user) ? processMessageInternal(message, chat, user) : null;
    }

    String processMessageInternal(Message message, Chat chat, ChatUser user);

    default boolean checkIsNotCommand(Message message) {
        return !message.getText().startsWith("/");
    }
}
