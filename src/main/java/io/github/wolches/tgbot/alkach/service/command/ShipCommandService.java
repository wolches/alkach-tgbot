package io.github.wolches.tgbot.alkach.service.command;

import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import io.github.wolches.tgbot.alkach.service.RandomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipCommandService implements CommandProcessingService {

    private static final String SHIP_COMMAND = "/ship";
    private static final String SHIP_TEXT_CHOOSING = "Шип-шип тестим: %s и %s (WIP)";
    private static final String SHIP_TEXT_RESULT = "Шип-шип тестим: %s и %s (WIP)";
    private static final String USER_LINK_FORMAT = "[%s](tg://user?id=%d)"; //TODO: добавить повсюду приписку формат

    private final RandomService randomService;

    @Override
    public String processMessageInternal(Message message, Chat chat, ChatUser user) {
        String userLinkA = getUserLink(chat);
        String userLinkB = getUserLink(chat);
        return String.format(SHIP_TEXT_RESULT, userLinkA, userLinkB);
    }

    private String getUserLink(Chat chat) {
        List<ChatUser> chatUsers = chat.getChatUsers();
        int chatUserId = randomService.getRandom().nextInt(chatUsers.size());

        String name = chatUsers.get(chatUserId).getUser().getLastUsername();
        Long id = chatUsers.get(chatUserId).getUser().getTelegramId();

        return String.format(USER_LINK_FORMAT, name, id);
    }

    @Override
    public String getCommandString() {
        return SHIP_COMMAND;
    }
}
