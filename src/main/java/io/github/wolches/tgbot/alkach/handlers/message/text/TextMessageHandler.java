package io.github.wolches.tgbot.alkach.handlers.message.text;

import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.Chat;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import org.telegram.telegrambots.meta.api.objects.Message;

import static io.github.wolches.tgbot.alkach.util.MessageUtils.hasText;
import static io.github.wolches.tgbot.alkach.util.MessageUtils.isCommand;

public interface TextMessageHandler {

    default boolean isApplicable(Message message, Chat chat, ChatUser user) {
        return  message != null &&
                hasText(message) &&
                !isCommand(message) &&
                isApplicableInternal(message, chat, user);
    }

    default boolean isApplicableInternal(Message message, Chat chat, ChatUser user) {
        return false;
    }

    String processMessageInternal(Message message, Chat chat, ChatUser user);
}
