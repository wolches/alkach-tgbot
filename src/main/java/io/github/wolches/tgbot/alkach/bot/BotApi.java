package io.github.wolches.tgbot.alkach.bot;

import io.github.wolches.tgbot.alkach.domain.model.chat.ChatUser;

public interface BotApi {

    void setBot(BotInstance bot);


    // TODO 1 Business logic exceptions
    // TODO 2 Refactor to more clear naming
    boolean isAdmin(ChatUser chatUser);

    // TODO 2 Refactor to more clear naming
    boolean isActive(ChatUser chatUser);
}
