package io.github.wolches.tgbot.alkach.persistance.repo;

import io.github.wolches.tgbot.alkach.domain.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.domain.model.chat.ChatUserSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatUserSettingsRepository extends JpaRepository<ChatUserSettings, Long> {

    default ChatUserSettings findByChatUserOrDefault(ChatUser chatUser) {
        return findByChatUser(chatUser).orElse(ChatUserSettings.createDefaultSettings(chatUser));
    }

    Optional<ChatUserSettings> findByChatUser(ChatUser chatUser);
}
