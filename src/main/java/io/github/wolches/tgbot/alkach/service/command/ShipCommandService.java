package io.github.wolches.tgbot.alkach.service.command;

import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatShippering;
import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import io.github.wolches.tgbot.alkach.domain.model.ChatUserShippering;
import io.github.wolches.tgbot.alkach.persistance.ShipperingDao;
import io.github.wolches.tgbot.alkach.persistance.repo.ChatUserRepository;
import io.github.wolches.tgbot.alkach.service.RandomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

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
        return shipperingDao
                .findLastChatShippering(chat)
                .map(csh -> csh.getShipperedAt().plusDays(1).isAfter(OffsetDateTime.now()) ? csh : null)
                .map(csh -> String.format(SHIP_TEXT_CHOOSED, getUserLink(csh.getShipperedA()), getUserLink(csh.getShipperedB())))
                .orElseGet(() -> {
                    ChatShippering csh = shipNewPairForChat(chat);
                    return String.format(SHIP_TEXT_CHOOSING, getUserLink(csh.getShipperedA()), getUserLink(csh.getShipperedB()));
                });
    }

    private ChatShippering shipNewPairForChat(Chat chat) { //TODO
        ChatShippering chatShippering = ChatShippering.builder()
                .chat(chat)
                .shipperedA(getRandomChatUser(chat))
                .shipperedB(getRandomChatUser(chat))
                .shipperedAt(OffsetDateTime.now())
                .build();

        ChatUserShippering.builder()
                .chatUser(chatShippering.getShipperedA())
                .shippedCount(1L)
                .build();

        ChatUserShippering.builder()
                .chatUser(chatShippering.getShipperedB())
                .shippedCount(1L)
                .build();
        return chatShippering;
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
