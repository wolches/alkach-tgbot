package io.github.wolches.tgbot.alkach.service.update;

import io.github.wolches.tgbot.alkach.domain.dto.ReplyDto;
import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import io.github.wolches.tgbot.alkach.domain.model.User;
import io.github.wolches.tgbot.alkach.repo.ChatRepository;
import io.github.wolches.tgbot.alkach.repo.ChatUserRepository;
import io.github.wolches.tgbot.alkach.repo.UserRepository;
import io.github.wolches.tgbot.alkach.service.message.MessageProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageUpdateService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatUserRepository chatUserRepository;
    private final List<MessageProcessingService> services;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<ReplyDto> processUpdate(Update update) {
        Message message = update.getMessage();

        ChatUser chatUser = findChatUserOrNew(
                findChatByTgIdOrNewAndUpdate(message.getChat()),
                findUserByTgIdOrNewAndUpdate(message.getFrom())
        );

        return services
                .stream()
                .filter(service -> service.isApplicable(message))
                .findAny()
                .map(service -> service.processMessage(message))
                .map(replyText ->
                        ReplyDto.builder()
                                .replyMessageId(message.getMessageId())
                                .chatId(chatUser.getChat().getTelegramId().toString())
                                .text(replyText)
                                .build());
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private ChatUser findChatUserOrNew(Chat chat, User user) {
        ChatUser chatUser = chat.getChatUsers().stream()
                .filter(ch -> ch.getUser().equals(user))
                .findFirst()
                .orElseGet(() -> {
                    ChatUser created = ChatUser.createNew(chat, user);
                    ChatUser cu = chatUserRepository.save(created);
                    chatRepository.addChatUserAndSave(chat, cu);
                    userRepository.addChatUserAndSave(user, cu);
                    return cu;
                });
        return chatUserRepository.incrementMessageCountAndSave(chatUser);
    }

    private Chat findChatByTgIdOrNewAndUpdate(org.telegram.telegrambots.meta.api.objects.Chat tgChat) {
        Chat chat = chatRepository
                .findByTelegramId(tgChat.getId())
                .orElse(Chat.createNew(tgChat.getId()));
        chat.setChatName(tgChat.getFirstName());
        return chatRepository.incrementMessageCountAndSave(chat);
    }

    private User findUserByTgIdOrNewAndUpdate(org.telegram.telegrambots.meta.api.objects.User tgUser) {
        User user = userRepository.incrementMessageCountAndSave(
                userRepository
                        .findByTelegramId(tgUser.getId())
                        .orElse(User.createNew(tgUser.getId()))
        );
        user.setLastUserTag(tgUser.getUserName());
        user.setLastUsername(tgUser.getFirstName());
        return user;
    }
}

