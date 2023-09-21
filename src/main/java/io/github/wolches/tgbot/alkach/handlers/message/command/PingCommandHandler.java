package io.github.wolches.tgbot.alkach.handlers.message.command;

import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.Chat;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PingCommandHandler implements CommandHandler {

    private static final String PING_COMMAND = "/ping";
    private static final String PING_TEXT = "Not very accurate ping: %d second(s).";

    @Override
    public String handle(Message message, Chat chat, ChatUser user) {
        long ping = Instant.now().toEpochMilli() - (message.getDate() * 1000);
        return String.format(PING_TEXT, ping);
    }

    @Override
    public String getCommandString() {
        return PING_COMMAND;
    }
}
