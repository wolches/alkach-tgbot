package io.github.wolches.tgbot.alkach.service.message.command;

import io.github.wolches.tgbot.alkach.bot.BotInstance;
import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import io.github.wolches.tgbot.alkach.persistance.repo.ChatUserRepository;
import io.github.wolches.tgbot.alkach.service.util.TextService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClearUsersCommandService implements CommandProcessingService {

    private static final String CLEAR_COMMAND = "/update_chat_users";
    private static final String CLEAR_TEXT =    "Данные о пользователях чата обновлены! \r\n" +
                                                "Список пользователей, чьи данные были заморожены: \r\n" +
                                                "%s";

    private final ChatUserRepository chatUserRepository;
    private final TextService textService;

    @Setter
    private BotInstance bot;

    @Override
    @SneakyThrows
    public String processMessageInternal(Message message, Chat chat, ChatUser user) { // TODO: Fix & Refactor & Rewrite
        if (bot.isUserAdmin(user)) {
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
                .filter(ChatUser::getActive)
                .filter(cu -> !bot.isChatUserActive(cu))
                .collect(Collectors.toList());
    }

    private String listChatUsers(List<ChatUser> toClear) {
        return textService.getListText(toClear, cu -> cu.getUser().getLastUsername());
    }
}
