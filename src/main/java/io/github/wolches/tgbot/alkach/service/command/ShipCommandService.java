package io.github.wolches.tgbot.alkach.service.command;

import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatShippering;
import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import io.github.wolches.tgbot.alkach.persistance.ShipperingDao;
import io.github.wolches.tgbot.alkach.persistance.repo.ChatUserRepository;
import io.github.wolches.tgbot.alkach.service.RandomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipCommandService implements CommandProcessingService {

    private static final String SHIP_COMMAND = "/ship";
    private static final String SHIP_TEXT_CHOOSING = "**Шип-шип...** \nПоиск новой пары дня: \n%s и %s ";
    private static final String SHIP_TEXT_CHOOSED = "**Шип-шип...** \nПара дня на сегодня уже есть: \n%s и %s ";
    private static final String USER_LINK_FORMAT = "[%s](tg://user?id=%d)";

    private final RandomService randomService;
    private final ShipperingDao shipperingDao;

    @Override
    public String processMessageInternal(Message message, Chat chat, ChatUser user) {
        return shipNewPairForChat(chat);
    }

    private String shipNewPairForChat(Chat chat) {
        ChatUser chatUserA = getRandomChatUser(chat);
        ChatUser chatUserB = getRandomChatUser(chat);

        ChatShippering.builder()
                .chatId(chat)
                .shipperedA(chatUserA)
                .shipperedB(chatUserB)
                .shipperedAt(OffsetDateTime.now())
                .build();




        return String.format(SHIP_TEXT_CHOOSING, getUserLink(chatUserA), getUserLink(chatUserA));
    }

    private ChatUser getRandomChatUser(Chat chat) {
        List<ChatUser> chatUsers = chat.getActiveChatUsers();
        int chatUserId = randomService.getRandom().nextInt(chatUsers.size());
        return chatUsers.get(chatUserId);
    }

    private String getUserLink(ChatUser chatUser) {
        String name = chatUser.getUser().getLastUsername();
        Long id = chatUser.getUser().getTelegramId();
        return String.format(USER_LINK_FORMAT, name, id);
    }

    @Override
    public String getCommandString() {
        return SHIP_COMMAND;
    }
}
