package io.github.wolches.tgbot.alkach.persistance.repo;

import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import io.github.wolches.tgbot.alkach.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByTelegramId(Long telegramId);

    /** TODO Criteria API */
    default User incrementMessageCountAndSave(User user) {
        user.incrementMessageCount();
        return save(user);
    }

    default User addChatUserAndSave(User user, ChatUser chatUser) {
        user.getUserInChats().add(chatUser);
        return save(user);
    }
}
