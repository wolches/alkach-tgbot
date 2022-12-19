package io.github.wolches.tgbot.alkach.persistance.repo;

import io.github.wolches.tgbot.alkach.domain.model.chat.Chat;
import io.github.wolches.tgbot.alkach.domain.model.chat.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByTelegramId(Long telegramId);

    /** TODO Criteria API */
    default Chat incrementMessageCountAndSave(Chat chat) {
        chat.incrementMessageCount();
        return save(chat);
    }

    default Chat addChatUserAndSave(Chat chat, ChatUser chatUser) {
        chat.getChatUsers().add(chatUser);
        return save(chat);
    }
}