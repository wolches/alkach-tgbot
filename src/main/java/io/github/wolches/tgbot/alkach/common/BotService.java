package io.github.wolches.tgbot.alkach.common;

import io.github.wolches.tgbot.alkach.bot.contract.BotApi;
import io.github.wolches.tgbot.alkach.bot.BotInstance;
import io.github.wolches.tgbot.alkach.persistence.model.chat.ChatUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BotService implements BotApi {

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

    // TODO 1 Business logic exceptions
    // TODO 2 Refactor to more clear naming
    @SneakyThrows
    public boolean isChatUserAdmin(ChatUser chatUser) {
        return bot.isUserAdmin(chatUser.getChat().getTelegramId(), chatUser.getUser().getTelegramId());
    }

    public boolean isChatUserActive(ChatUser chatUser) {
        return bot.isChatUserActive(chatUser.getChat().getTelegramId(), chatUser.getUser().getTelegramId());
    }
}
