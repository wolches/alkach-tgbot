package io.github.wolches.tgbot.alkach.service.command;

import io.github.wolches.tgbot.alkach.service.message.MessageProcessingService;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface CommandProcessingService extends MessageProcessingService {

    default boolean isApplicable(Message message) {
        return !checkIsNotCommand(message) && isApplicableInternal(message);
    }
}
