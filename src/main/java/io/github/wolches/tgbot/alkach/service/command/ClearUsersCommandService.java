package io.github.wolches.tgbot.alkach.service.command;

import io.github.wolches.tgbot.alkach.bot.BotInstance;
import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import io.github.wolches.tgbot.alkach.repo.ChatUserRepository;
import io.github.wolches.tgbot.alkach.service.RandomService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ClearUsersCommandService implements CommandProcessingService {

    private static final String CLEAR_COMMAND = "/update_chat_users";
    private static final String CLEAR_TEXT =    "Данные о пользователях чата обновлены! \r\n" +
                                                "Список пользователей, чьи данные были заморожены: \r\n" +
                                                "%s";

    private final ChatUserRepository chatUserRepository;

    @Setter(onMethod_ = {@Autowired})
    private BotInstance bot;

    @Override
    @SneakyThrows
    public String processMessageInternal(Message message, Chat chat, ChatUser user) {
        if (bot
                .getChatAdminsIds(chat.getTelegramId())
                .contains(user.getUser().getTelegramId())
        ) {
            List<ChatUser> usersToClear = getChatUsersToClear(chat);
            usersToClear.forEach(cu -> cu.setActive(false));
            chatUserRepository.saveAll(usersToClear);
            return String.format(CLEAR_TEXT, listChatUsers(usersToClear));
        }
        return null;
    }

    @Override
    public String getCommandString() {
        return CLEAR_COMMAND;
    }

    private List<ChatUser> getChatUsersToClear(Chat chat) {
        return chat
                .getChatUsers().stream()
                .filter(cu -> !bot.isChatUserActive(chat.getTelegramId(), cu.getUser().getTelegramId()))
                .collect(Collectors.toList());
    }

    private String listChatUsers(List<ChatUser> toClear) {
        StringBuilder userListBuilder = new StringBuilder();
        for (int i = 0, size = toClear.size(); i < size; i++) {
            userListBuilder
                    .append(i + 1)
                    .append(". ")
                    .append(toClear.get(i).getUser().getLastUsername())
                    .append(i + 1 == size ? "" : " \r\n");
        }
        return userListBuilder.toString();
    }
}
