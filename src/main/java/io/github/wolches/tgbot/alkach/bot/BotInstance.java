package io.github.wolches.tgbot.alkach.bot;


import io.github.wolches.tgbot.alkach.pipeline.UpdatePipeline;
import io.github.wolches.tgbot.alkach.domain.dto.ReplyMessageDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class BotInstance extends TelegramLongPollingBot {

    private final UpdatePipeline updatePipeline;
    private final BotProxyService botService;

    protected final String botToken;
    protected final String botUsername;

    @PostConstruct
    public void init() {
        if (!botService.isInitialized()) {
            botService.setBot(this);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("#onUpdateReceived({}): Recieved update", update.getUpdateId());
        updatePipeline.processUpdate(update);
    }

    public boolean isUserAdmin(Long chatTgId, Long userTgId) throws TelegramApiException {
        return execute(
                new GetChatAdministrators(chatTgId.toString())).stream()
                    .map(ChatMember::getUser)
                    .map(User::getId)
                    .collect(Collectors.toList())
                .contains(userTgId);
    }

    public boolean isChatUserActive(Long chatId, Long userId) {
        try {
            return  execute(new GetChatMember(chatId.toString(), userId))
                    .getUser().getId().equals(userId);
        } catch (TelegramApiException e) {
            log.error("Error checking is user active", e);
            e.printStackTrace();
        }
        return false;
    }
}
