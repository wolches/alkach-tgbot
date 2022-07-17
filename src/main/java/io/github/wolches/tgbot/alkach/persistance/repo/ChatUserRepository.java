package io.github.wolches.tgbot.alkach.persistance.repo;

import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {

    /** TODO Criteria API */
    default ChatUser incrementMessageCountAndSave(ChatUser chatUser) {
        chatUser.incrementMessageCount();
        return save(chatUser);
    }

    List<ChatUser> findAllByChatAndActive(Chat chat, boolean active);
}
