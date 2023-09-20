package io.github.wolches.tgbot.alkach.bot.contract;

import io.github.wolches.tgbot.alkach.bot.BotInstance;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;

public interface BotApi {

    void setBot(BotInstance bot);


    // TODO 1 Business logic exceptions
    // TODO 2 Refactor to more clear naming
    boolean isChatUserAdmin(ChatUser chatUser);

    // TODO 2 Refactor to more clear naming
    boolean isChatUserActive(ChatUser chatUser);
}
