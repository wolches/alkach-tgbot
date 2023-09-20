package io.github.wolches.tgbot.alkach.domain.persistence.repo;

import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUserSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatUserSettingsRepository extends JpaRepository<ChatUserSettings, Long> {

    default ChatUserSettings findByChatUserOrDefault(ChatUser chatUser) {
        return findByChatUser(chatUser).orElse(ChatUserSettings.withUser(chatUser));
    }

    Optional<ChatUserSettings> findByChatUser(ChatUser chatUser);
}
