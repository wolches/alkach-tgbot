package io.github.wolches.tgbot.alkach.service.common;

import io.github.wolches.tgbot.alkach.bot.BotApi;
import io.github.wolches.tgbot.alkach.bot.BotInstance;
import io.github.wolches.tgbot.alkach.domain.model.chat.ChatUser;
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
    public boolean isAdmin(ChatUser chatUser) {
        return bot.isUserAdmin(chatUser.getChat().getTelegramId(), chatUser.getUser().getTelegramId());
    }

    public boolean isActive(ChatUser chatUser) {
        return bot.isChatUserActive(chatUser.getChat().getTelegramId(), chatUser.getUser().getTelegramId());
    }
}
