package io.github.wolches.tgbot.alkach.service.common;

import io.github.wolches.tgbot.alkach.bot.BotInstance;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BotService {

    private BotInstance bot;

    @Getter
    private boolean initialized = false;

    /**
     *  TODO: Implement this proxy object service
     */
    @Deprecated
    public BotInstance getBot() {
        return bot;
    }

    public void setBot(BotInstance bot) {
        if (!initialized && this.bot == null) {
            this.bot = bot;
            initialized = true;
        }
    }
}
