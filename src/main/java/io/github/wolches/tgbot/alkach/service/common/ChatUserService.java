package io.github.wolches.tgbot.alkach.service.common;

import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import io.github.wolches.tgbot.alkach.domain.model.User;
import io.github.wolches.tgbot.alkach.persistance.repo.ChatRepository;
import io.github.wolches.tgbot.alkach.persistance.repo.ChatUserRepository;
import io.github.wolches.tgbot.alkach.persistance.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class ChatUserService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatUserRepository chatUserRepository;

    public ChatUser getChatUserForMessage(org.telegram.telegrambots.meta.api.objects.Message message) {
        return findChatUserOrNew(
                findChatByTgIdOrNewAndUpdate(message.getChat()),
                findUserByTgIdOrNewAndUpdate(message.getFrom())
        );
    }

    public ChatUser findChatUserOrNew(Chat chat, User user) {
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
        return chatUserRepository.incrementMessageCountAndSave(chatUser);
    }

    public Chat findChatByTgIdOrNewAndUpdate(org.telegram.telegrambots.meta.api.objects.Chat tgChat) {
        Chat chat = chatRepository
                .findByTelegramId(tgChat.getId())
                .orElse(Chat.createNew(tgChat.getId()));
        chat.setChatName(tgChat.getFirstName());
        return chatRepository.incrementMessageCountAndSave(chat);
    }

    public User findUserByTgIdOrNewAndUpdate(org.telegram.telegrambots.meta.api.objects.User tgUser) {
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
