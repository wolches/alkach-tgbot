package io.github.wolches.tgbot.alkach.service.common;

import io.github.wolches.tgbot.alkach.domain.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.domain.model.chat.ChatUserSettings;
import io.github.wolches.tgbot.alkach.domain.model.user.User;
import io.github.wolches.tgbot.alkach.domain.model.user.UserSettings;
import io.github.wolches.tgbot.alkach.persistance.repo.ChatUserRepository;
import io.github.wolches.tgbot.alkach.persistance.repo.ChatUserSettingsRepository;
import io.github.wolches.tgbot.alkach.persistance.repo.UserRepository;
import io.github.wolches.tgbot.alkach.persistance.repo.UserSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSettingsService {
    private final UserRepository userRepository;
    private final ChatUserRepository chatUserRepository;
    private final ChatUserSettingsRepository chatUserSettingsRepository;
    private final UserSettingsRepository userSettingsRepository;

    public ChatUser initChatUserSettingsIfNotExists(ChatUser chatUser) {
        if (chatUser.getSettings() == null) {
            ChatUserSettings settings = ChatUserSettings.createDefaultSettings(chatUser);
            settings = chatUserSettingsRepository.save(settings);
            chatUser.setSettings(settings);
            chatUser = chatUserRepository.save(chatUser);
        }
        return chatUser;
    }

    public User initUserSettingsIfNotExists(User user) {
        if (user.getSettings() == null) {
            UserSettings settings = UserSettings.createDefaultSettings(user);
            settings = userSettingsRepository.save(settings);
            user.setSettings(settings);
            user = userRepository.save(user);
        }
        return user;
    }
}
