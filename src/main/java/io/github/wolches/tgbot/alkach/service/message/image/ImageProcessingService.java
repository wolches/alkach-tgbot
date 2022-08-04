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
}
