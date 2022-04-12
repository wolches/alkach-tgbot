package io.github.wolches.tgbot.alkach.service.command;

import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import io.github.wolches.tgbot.alkach.service.RandomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class CoinCommandService implements CommandProcessingService {

    private static final String COIN_COMMAND = "/coin";
    private static final String COIN_TEXT = "Бросаю монетку, выпадает %s.";
    private static final String COIN_HEADS = "орел";
    private static final String COIN_TAILS = "решка";

    private final RandomService randomService;

    @Override
    public String processMessageInternal(Message message, Chat chat, ChatUser user) {
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
