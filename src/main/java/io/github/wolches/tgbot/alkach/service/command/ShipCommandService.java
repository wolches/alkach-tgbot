package io.github.wolches.tgbot.alkach.service.command;

import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import org.telegram.telegrambots.meta.api.objects.Message;

//TODO
public class ShipCommandService implements CommandProcessingService {

    private static final String SHIP_COMMAND = "/ship";
    private static final String SHIP_TEXT_CHOOSING = "";
    private static final String SHIP_TEXT_RESULT = "";

    @Override
    public String processMessageInternal(Message message, Chat chat, ChatUser user) {
        return null;
    }

    @Override
    public String getCommandString() {
        return SHIP_COMMAND;
    }
}
