package io.github.wolches.tgbot.alkach.service.message;

import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.Chat;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageProcessingService {

    default boolean isApplicable(Message message, Chat chat, ChatUser user) {
        return  message != null &&
                isApplicableInternal(message, chat, user);
    }

    default boolean isApplicableInternal(Message message, Chat chat, ChatUser user) {
        return false;
    }

    default String processMessage(Message message, Chat chat, ChatUser user) {
        return isApplicable(message, chat, user) ? processMessageInternal(message, chat, user) : null;
    }

    String processMessageInternal(Message message, Chat chat, ChatUser user);
}
