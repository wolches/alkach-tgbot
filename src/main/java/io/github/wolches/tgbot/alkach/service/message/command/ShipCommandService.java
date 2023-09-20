package io.github.wolches.tgbot.alkach.service.message.command;

import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.Chat;
import io.github.wolches.tgbot.alkach.domain.persistence.model.ship.ChatShippering;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.domain.persistence.model.ship.ChatUserShippering;
import io.github.wolches.tgbot.alkach.domain.persistence.ShipperingDao;
import io.github.wolches.tgbot.alkach.util.RandomService;
import io.github.wolches.tgbot.alkach.util.TextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipCommandService implements CommandProcessingService {

    private static final String SHIP_COMMAND = "/ship";
    private static final String SHIP_TEXT_CHOOSING =
            "**Шип-шип...** \r\n" +
            "**Поиск новой пары дня:** \r\n" +
            "%s и %s ";
    private static final String SHIP_TEXT_CHOOSED =
            "**Шип-шип...** \r\n" +
            "**Пара дня на сегодня уже есть:** \r\n" +
            "%s и %s ";

    private final RandomService randomService;
    private final ShipperingDao shipperingDao;
    private final TextService textService;

    @Override
    public String processMessageInternal(Message message, Chat chat, ChatUser user) {
        return shipperingDao
                .findLastChatShippering(chat)
                .map(csh -> csh.getShipperedAt().plusDays(1).isAfter(OffsetDateTime.now()) ? csh : null)
                .map(csh -> String.format(SHIP_TEXT_CHOOSED, textService.getUserLink(csh.getShipperedA()), textService.getUserLink(csh.getShipperedB())))
                .orElseGet(() -> {
                    ChatShippering csh = shipNewPairForChat(chat);
                    return String.format(SHIP_TEXT_CHOOSING, textService.getUserLink(csh.getShipperedA()), textService.getUserLink(csh.getShipperedB()));
                });
    }

    private ChatShippering shipNewPairForChat(Chat chat) {
        ChatUser userA = getRandomChatUser(chat);
        ChatUser userB = getRandomChatUser(chat, userA);
        ChatShippering chatShippering = shipperingDao
                .saveChatShippering(
                        ChatShippering.builder()
                                .chat(chat)
                                .shipperedA(userA)
                                .shipperedB(userB)
                                .shipperedAt(OffsetDateTime.now())
                                .build()
        );
        incrementShippedCounterForChatUser(userA);
        incrementShippedCounterForChatUser(userB);

        return chatShippering;
    }

    private void incrementShippedCounterForChatUser(ChatUser chatUser) {
        ChatUserShippering chatUserShippering = shipperingDao
                .findChatUserShippering(chatUser)
                .orElse(
                        ChatUserShippering.builder()
                                .chatUser(chatUser)
                                .shippedCount(0L)
                                .build()
                );
        chatUserShippering.setShippedCount(chatUserShippering.getShippedCount() + 1L);
        shipperingDao.saveChatUserShippering(chatUserShippering);
    }

    private ChatUser getRandomChatUser(Chat chat) {
        List<ChatUser> chatUsers = chat.getActiveChatUsers();
        int chatUserId = randomService.getRandom().nextInt(chatUsers.size());
        return chatUsers.get(chatUserId);
    }

    private ChatUser getRandomChatUser(Chat chat, ChatUser ... exclude) {
        List<ChatUser> chatUsers = chat.getActiveChatUsers();
        for (ChatUser user : exclude) {
            chatUsers.remove(user);
        }
        int chatUserId = randomService.getRandom().nextInt(chatUsers.size());
        return chatUsers.get(chatUserId);
    }

    @Override
    public String getCommandString() {
        return SHIP_COMMAND;
    }
}
