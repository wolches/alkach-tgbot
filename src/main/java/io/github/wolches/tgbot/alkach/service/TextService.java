package io.github.wolches.tgbot.alkach.service;

import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import lombok.RequiredArgsConstructor;
import org.jvnet.hk2.annotations.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TextService {

    private static final String USER_LINK_FORMAT = "[%s](tg://user?id=%d)";

    public String getUserLink(ChatUser chatUser) {
        String name = chatUser.getUser().getLastUsername();
        Long id = chatUser.getUser().getTelegramId();
        return String.format(USER_LINK_FORMAT, name, id);
    }

    public <E> String getListText(List<E> listSource, Function<E, String> formatFunction) {
        StringBuilder listBuilder = new StringBuilder();
        for (int i = 0, size = listSource.size(); i < size; i++) {
            listBuilder
                    .append(i + 1)
                    .append(". ")
                    .append(formatFunction.apply(listSource.get(i)))
                    .append(i + 1 == size ? "" : " \r\n");
        }
        return listBuilder.toString();
    }
}