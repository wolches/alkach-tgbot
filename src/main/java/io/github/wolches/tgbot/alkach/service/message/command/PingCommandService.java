package io.github.wolches.tgbot.alkach.service.message.command;

import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PingCommandService implements CommandProcessingService {

    private static final String PING_COMMAND = "/ping";
    private static final String PING_TEXT = "Not very accurate ping: %d second(s).";

    @Override
    public String processMessageInternal(Message message, Chat chat, ChatUser user) {
        long ping = Instant.now().getEpochSecond() - message.getDate();
        return String.format(PING_TEXT, ping);
    }

    @Override
    public String getCommandString() {
        return PING_COMMAND;
    }
}
