package io.github.wolches.tgbot.alkach.service.user;

import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.Chat;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.domain.persistence.model.user.User;
import io.github.wolches.tgbot.alkach.domain.persistence.repo.ChatRepository;
import io.github.wolches.tgbot.alkach.domain.persistence.repo.ChatUserRepository;
import io.github.wolches.tgbot.alkach.domain.persistence.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatUserService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatUserRepository chatUserRepository;
    private final UserSettingsService userSettingsService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ChatUser getChatUserForMessage(org.telegram.telegrambots.meta.api.objects.Message message) {
        return findChatUserOrNew(
                findChatByTgIdOrNewAndUpdate(message.getChat()),
                findUserByTgIdOrNewAndUpdate(message.getFrom())
        );
    }

    private Chat findChatByTgIdOrNewAndUpdate(org.telegram.telegrambots.meta.api.objects.Chat tgChat) {
        Chat chat = chatRepository
                .findByTelegramId(tgChat.getId())
                .orElse(Chat.createNew(tgChat));
        chat.setChatName(tgChat.getFirstName());
        return chatRepository.incrementMessageCountAndSave(chat);
    }

    private User findUserByTgIdOrNewAndUpdate(org.telegram.telegrambots.meta.api.objects.User tgUser) {
        User user = userRepository
                .findByTelegramId(tgUser.getId())
                .orElse(User.createNew(tgUser.getId()));
        user.setLastUserTag(tgUser.getUserName());
        user.setLastUsername(tgUser.getFirstName());
        user = userRepository.incrementMessageCountAndSave(user);
        return userSettingsService.initUserSettingsIfNotExists(user);
    }

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
        chatUser.setActive(true);
        chatUser = chatUserRepository.incrementMessageCountAndSave(chatUser);
        return userSettingsService.initChatUserSettingsIfNotExists(chatUser);
    }
}
