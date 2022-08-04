package io.github.wolches.tgbot.alkach.persistance.repo;

import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import io.github.wolches.tgbot.alkach.domain.model.ChatUserSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatUserSettingsRepository extends JpaRepository<ChatUserSettings, Long> {

    default ChatUserSettings findByChatUserOrDefault(ChatUser chatUser) {
        ChatUserSettings settings =
                findByChatUser(chatUser)
                        .orElse(save(ChatUserSettings.createDefaultSettings(chatUser)));
        chatUser.setSettings(settings);
        return settings;
    }

    Optional<ChatUserSettings> findByChatUser(ChatUser chatUser);
}
