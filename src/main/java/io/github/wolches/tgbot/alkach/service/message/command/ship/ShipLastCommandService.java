package io.github.wolches.tgbot.alkach.service.message.command.ship;

import io.github.wolches.tgbot.alkach.domain.model.chat.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ship.ChatShippering;
import io.github.wolches.tgbot.alkach.domain.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.persistance.ShipperingDao;
import io.github.wolches.tgbot.alkach.service.util.TextService;
import io.github.wolches.tgbot.alkach.service.message.command.CommandProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipLastCommandService implements CommandProcessingService {

    private static final String SHIP_STAT_COMMAND = "/ship_last";
    private static final String SHIP_STAT_TEXT =
            "**Шип-шип...**\r\n " +
            "**Последние парочки в этой конфе:** \r\n" +
            "%s";


    private final TextService textService;
    private final ShipperingDao shipperingDao;

    @Override
    public String processMessageInternal(Message message, Chat chat, ChatUser user) {
        List<ChatShippering> ships = shipperingDao.findChatShippering(chat);
        ships.sort(Comparator.comparing(ChatShippering::getShipperedAt).reversed());
        return String.format(
                SHIP_STAT_TEXT,
                textService.getListText(ships.subList(0, ships.size() > 4 ? 5 : ships.size()), textService::getShipperingHistoryRow)
        );
    }

    @Override
    public String getCommandString() {
        return SHIP_STAT_COMMAND;
    }
}
