package io.github.wolches.tgbot.alkach.service.message.command;

import io.github.wolches.tgbot.alkach.domain.model.chat.Chat;
import io.github.wolches.tgbot.alkach.domain.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.domain.model.user.UserSettings;
import io.github.wolches.tgbot.alkach.persistance.repo.UserSettingsRepository;
import io.github.wolches.tgbot.alkach.service.util.SecurityService;
import io.github.wolches.tgbot.alkach.service.util.TextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginCommandService implements CommandProcessingService {

    private static final String LOGIN_COMMAND             = "/login";
    private static final String RESTRICTED_AT_GROUP_CHAT  = "Login is restricted at group chats!";
    private static final String PASSWORD_REJECTED         = "Access denied: incorrect password";
    private static final String PASSWORD_ACCEPTED         = "Access granted: %s";
    private static final String EXCEPTION_OCCURED_MESSAGE = "An exception has occured, check logs for additional info.";
    private static final String SUPERUSER_PWD_HASH        = "d29sY2hlcw==EzJfhhENWN9Zh0Onne9QkQ=="; // TODO: External config

    private final TextService textService;
    private final SecurityService securityService;
    private final UserSettingsRepository settingsRepository;

    @Override
    public String processMessageInternal(Message message, Chat chat, ChatUser user) {
        try {
            String[] commandArguments = textService.getCommandArguments(LOGIN_COMMAND, message.getText());
            if (!isPrivateChat(chat)) return RESTRICTED_AT_GROUP_CHAT;
            if (commandArguments.length != 1) return PASSWORD_REJECTED;
            String passwordHash = securityService.getPasswordHashForUser(user.getUser().getLastUserTag(), commandArguments[0]);
            if (SUPERUSER_PWD_HASH.equals(passwordHash)) {
                Optional<UserSettings> settings = settingsRepository.findByUser(user.getUser());
                settings.ifPresent(us -> us.setAdmin(true));
                settingsRepository.save(settings.orElseThrow());
                return String.format(PASSWORD_ACCEPTED, user.getUser().getLastUsername());
            }
            return PASSWORD_REJECTED;
        } catch (Throwable e) {
            log.error("An exception has occured when executing '{}'", message.getText(), e);
            return EXCEPTION_OCCURED_MESSAGE;
        }
    }

    private boolean isPrivateChat(Chat chat) {
        return chat.isUser() && !chat.isGroup() && !chat.isSuperGroup() && !chat.isChannel();
    }

    @Override
    public String getCommandString() {
        return LOGIN_COMMAND;
    }
}
