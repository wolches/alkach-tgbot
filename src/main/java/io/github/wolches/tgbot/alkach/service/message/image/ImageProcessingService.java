package io.github.wolches.tgbot.alkach.service.message.image;

import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import io.github.wolches.tgbot.alkach.service.message.MessageProcessingService;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface ImageProcessingService extends MessageProcessingService {

    default boolean isApplicable(Message message, Chat chat, ChatUser user) {
        return  message != null &&
                message.hasPhoto() &&
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
