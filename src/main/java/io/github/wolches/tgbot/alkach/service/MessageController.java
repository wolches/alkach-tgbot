package io.github.wolches.tgbot.alkach.service;

import io.github.wolches.tgbot.alkach.domain.context.UpdateContext;
import io.github.wolches.tgbot.alkach.domain.dto.ReplyMessageDto;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.pipeline.pipe.Pipeline;
import io.github.wolches.tgbot.alkach.pipeline.pipe.PipelineBuilder;
import io.github.wolches.tgbot.alkach.service.common.ChatUserService;
import io.github.wolches.tgbot.alkach.service.message.MessageProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageController {

    private final ChatUserService chatUserService;
    private final List<MessageProcessingService> services;

    public Optional<ReplyMessageDto> processMessageUpdate(UpdateContext update) {
        Message message = update.getUpdate().getMessage();
        ChatUser chatUser = chatUserService.getChatUserForMessage(message);
        return services
                .stream()
                .filter(service -> service.isApplicable(message, chatUser.getChat(), chatUser))
                .map(service -> service.processMessage(message, chatUser.getChat(), chatUser))
                .map(replyText ->
                        ReplyMessageDto.builder()
                                .replyMessageId(message.getMessageId())
                                .chatId(chatUser.getChat().getTelegramId().toString())
                                .replyText(replyText)
                                .build())
                .findAny();
    }
}

