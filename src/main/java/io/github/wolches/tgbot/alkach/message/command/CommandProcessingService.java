package io.github.wolches.tgbot.alkach.message.command;

import io.github.wolches.tgbot.alkach.persistence.model.chat.Chat;
import io.github.wolches.tgbot.alkach.persistence.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.message.text.TextMessageProcessingService;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface CommandProcessingService extends TextMessageProcessingService {

    default boolean isApplicable(Message message, Chat chat, ChatUser user) {
        return  message != null &&
                hasText(message) &&
                isCommand(message) &&
                isApplicableInternal(message, chat, user);
    }

    @Override
    default boolean isApplicableInternal(Message message, Chat chat, ChatUser user) {
        return message.getText().startsWith(getCommandString());
    }

    String getCommandString();
}
