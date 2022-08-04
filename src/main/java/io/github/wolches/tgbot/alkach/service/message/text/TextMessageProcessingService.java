package io.github.wolches.tgbot.alkach.service.message.text;

import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import io.github.wolches.tgbot.alkach.service.message.MessageProcessingService;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface TextMessageProcessingService extends MessageProcessingService {

    @Override
    default boolean isApplicable(Message message, Chat chat, ChatUser user) {
        return  message != null &&
                hasText(message) &&
                !isCommand(message) &&
                isApplicableInternal(message, chat, user);
    }

    default boolean hasText(Message message) {
        return  message.getText() != null &&
                !message.getText().equals("");
    }

    default boolean isCommand(Message message) {
        return message.getText().startsWith("/");
    }
}
