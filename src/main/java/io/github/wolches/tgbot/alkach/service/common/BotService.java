package io.github.wolches.tgbot.alkach.service.common;

import io.github.wolches.tgbot.alkach.bot.BotInstance;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BotService {

    @Setter
    private BotInstance bot;

    /**
     *  TODO: Implement this proxy object service
     */
    @Deprecated
    public BotInstance getBot() {
        return bot;
    }
}
