package io.github.wolches.tgbot.pesda.repo;

import io.github.wolches.tgbot.pesda.domain.model.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {

    /** TODO Criteria API */
    default ChatUser incrementMessageCountAndSave(ChatUser chatUser) {
        chatUser.incrementMessageCount();
        return save(chatUser);
    }
}
