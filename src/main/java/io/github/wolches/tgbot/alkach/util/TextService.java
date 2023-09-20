package io.github.wolches.tgbot.alkach.util;

import io.github.wolches.tgbot.alkach.domain.persistence.model.ship.ChatShippering;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TextService {

    private static final String USER_LINK_FORMAT = "[%s](tg://user?id=%d)";
    private static final String SHIP_HISTORY_ROW_FORMAT = "%s и %s : %s";

    public String getUserLink(ChatUser chatUser) {
        String name = chatUser.getUser().getLastUsername();
        Long id = chatUser.getUser().getTelegramId();
        return String.format(USER_LINK_FORMAT, name, id);
    }

    public <E> String getListText(List<E> listSource, Function<E, String> formatFunction) {
        StringJoiner stringJoiner = new StringJoiner(", ");
        listSource.forEach(text -> stringJoiner.add(formatFunction.apply(text)));
        return stringJoiner.toString();
    }

    public String getShipperingHistoryRow(ChatShippering chatShippering) {
        return String.format(
                SHIP_HISTORY_ROW_FORMAT,
                getUserLink(chatShippering.getShipperedA()),
                getUserLink(chatShippering.getShipperedB()),
                chatShippering.getShipperedAt().format(DateTimeFormatter.ISO_LOCAL_DATE)
        );
    }

    /**
     *   >> "/start e=12 b=1"
     *   << ["e=12", "b=1"]
     */
    public String[] getCommandArguments(String command, String commandQuery) {
        String[] tokens = commandQuery.split(" ");
        if (tokens.length > 1) {
            if (!tokens[0].equals(command)) {
                throw new IllegalArgumentException("Command name does not match given query!");
            }
            return Arrays.copyOfRange(tokens, 1, tokens.length);
        }
        return new String[0];
    }
}
