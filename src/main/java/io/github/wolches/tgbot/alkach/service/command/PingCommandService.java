package io.github.wolches.tgbot.alkach.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PingCommandService implements CommandProcessingService {

    private static final String PING_COMMAND = "/ping";
    private static final String PING_TEXT = "Ping: %d second(s).";

    @Override
    public boolean isApplicableInternal(Message message) {
        return message.getText().equalsIgnoreCase(PING_COMMAND);
    }

    @Override
    public String processMessageInternal(Message message) {
        long ping = Instant.now().getEpochSecond() - message.getDate();
        return String.format(PING_TEXT, ping);
    }
}
