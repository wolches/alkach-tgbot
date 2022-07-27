package io.github.wolches.tgbot.alkach.service.command;

import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatShippering;
import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import io.github.wolches.tgbot.alkach.domain.model.ChatUserShippering;
import io.github.wolches.tgbot.alkach.persistance.ShipperingDao;
import io.github.wolches.tgbot.alkach.service.TextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShipStatCommandService implements CommandProcessingService {

    private static final String SHIP_STAT_COMMAND = "/ship_stat";
    private static final String SHIP_STAT_TEXT =
            "**Шип-шип...**\r\n " +
            "**Ваша статистика:** %s \r\n" +
            "Зашипперено: %d раз \r\n" +
            "Последние пять пар в которые вы вошли: \r\n" +
            "%s";

    private static final String SHIP_HISTORY_ROW_FORMAT = "%s и %s : %s";

    private final TextService textService;
    private final ShipperingDao shipperingDao;

    @Override
    public String processMessageInternal(Message message, Chat chat, ChatUser user) {
        List<ChatShippering> ships = shipperingDao.findChatShipperingByChatUser(user);
        ships.sort(Comparator.comparing(ChatShippering::getShipperedAt).reversed());
        Optional<ChatUserShippering> shipCount = shipperingDao.findChatUserShippering(user);
        return String.format(
                SHIP_STAT_TEXT,
                textService.getUserLink(user),
                shipCount.map(ChatUserShippering::getShippedCount).orElse(0L),
                textService.getListText(ships.subList(0, ships.size() > 4 ? 5 : ships.size()), this::getShipperingHistoryRow)
        );
    }

    private String getShipperingHistoryRow(ChatShippering chatShippering) {
        return String.format(
                SHIP_HISTORY_ROW_FORMAT,
                textService.getUserLink(chatShippering.getShipperedA()),
                textService.getUserLink(chatShippering.getShipperedB()),
                chatShippering.getShipperedAt().format(DateTimeFormatter.ISO_LOCAL_DATE)
        );
    }

    @Override
    public String getCommandString() {
        return SHIP_STAT_COMMAND;
    }
}
