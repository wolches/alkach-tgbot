package io.github.wolches.tgbot.alkach.bot;


import io.github.wolches.tgbot.alkach.controller.UpdateController;
import io.github.wolches.tgbot.alkach.domain.dto.ResultDto;
import io.github.wolches.tgbot.alkach.domain.dto.UpdateProcessingResultDto;
import io.github.wolches.tgbot.alkach.domain.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.service.common.BotService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class BotInstance extends TelegramLongPollingBot {

    private final UpdateController updateController;
    private final BotService botService;

    protected final String botToken;
    protected final String botUsername;

    private boolean isInitialized = false;

    @PostConstruct
    public void init() {
        if (!isInitialized) botService.setBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        Optional<UpdateProcessingResultDto> result = updateController.processUpdate(update);
        result.ifPresent(r -> replyTextMessage(r.getMessageProcessingResultDto()));
    }

    private List<Long> getChatAdminsTelegramIds(Long chatId) throws TelegramApiException {
        return execute(
                new GetChatAdministrators(chatId.toString())).stream()
                .map(ChatMember::getUser)
                .map(User::getId)
                .collect(Collectors.toList());
    }

    private void replyTextMessage(Optional<ResultDto> reply) {
        reply.ifPresent(r ->  sendMessage(r.getChatId(), r.getReplyMessageId(), r.getReplyText()));
    }

    public boolean isUserAdmin(ChatUser chatUser) throws TelegramApiException {
        return getChatAdminsTelegramIds(chatUser.getChat().getTelegramId())
                .contains(chatUser.getUser().getTelegramId());
    }

    private boolean isChatUserActive(Long chatId, Long userId) {
        try {
            return  execute(new GetChatMember(chatId.toString(), userId))
                    .getUser().getId().equals(userId);
        } catch (TelegramApiException e) {
            log.error("Error checking is user active", e);
            e.printStackTrace();
        }
        return false;
    }

    public boolean isChatUserActive(ChatUser chatUser) {
        return isChatUserActive(chatUser.getChat().getTelegramId(), chatUser.getUser().getTelegramId());
    }

    private void sendMessage(String chatId, Integer replyMessageId, String text) {
        try {
            SendMessage msg = new SendMessage();
            msg.setChatId(chatId);
            msg.setParseMode("Markdown");
            msg.setReplyToMessageId(replyMessageId);
            msg.setText(text);
            execute(msg);
        } catch (TelegramApiException e) {
            log.error("Error sending message", e);
            e.printStackTrace();
        }
    }
}
