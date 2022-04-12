package io.github.wolches.tgbot.alkach.service.command;

import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import io.github.wolches.tgbot.alkach.repo.ChatUserRepository;
import io.github.wolches.tgbot.alkach.service.RandomService;
import lombok.RequiredArgsConstructor;
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

    private static final String CLEAR_COMMAND = "/clear_users";
    private static final String CLEAR_TEXT =    "Данные о пользователях чата обновлены! \r\n" +
                                                "Список пользователей, чьи данные были очищены: \r\n" +
                                                "%s";

    private final ChatUserRepository chatUserRepository;

    @Override
    public String processMessageInternal(Message message, Chat chat, ChatUser user) {
        List<ChatUser> usersToClear = getChatUsersToClear(chat);
        usersToClear.forEach(cu -> cu.setActive(false));
        chatUserRepository.saveAll(usersToClear);
        return String.format(CLEAR_TEXT, listChatUsers(usersToClear));
    }

    @Override
    public String getCommandString() {
        return CLEAR_COMMAND;
    }

    private List<ChatUser> getChatUsersToClear(Chat chat) {
        //TODO
        return new ArrayList<>();
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
