package io.github.wolches.tgbot.alkach.service.command;

import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import io.github.wolches.tgbot.alkach.service.message.MessageProcessingService;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface CommandProcessingService extends MessageProcessingService {

    default boolean isApplicable(Message message, Chat chat, ChatUser user) {
        return !checkIsNotCommand(message) && isApplicableInternal(message, chat, user);
    }

    @Override
    default boolean isApplicableInternal(Message message, Chat chat, ChatUser user) {
        return message.getText().equalsIgnoreCase(getCommandString());
    }

    String getCommandString();
}
