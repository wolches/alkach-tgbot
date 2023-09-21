package io.github.wolches.tgbot.alkach.handlers.message.command;

import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.Chat;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.service.common.RandomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class CoinCommandHandler implements CommandHandler {

    private static final String COIN_COMMAND = "/coin";
    private static final String COIN_TEXT = "Бросаю монетку, выпадает %s.";
    private static final String COIN_HEADS = "орел";
    private static final String COIN_TAILS = "решка";

    private final RandomService randomService;

    @Override
    public String handle(Message message, Chat chat, ChatUser user) {
        return String.format(
               COIN_TEXT, randomService.getRandom().nextBoolean() ?
               COIN_HEADS : COIN_TAILS
        );
    }

    @Override
    public String getCommandString() {
        return COIN_COMMAND;
    }
}
