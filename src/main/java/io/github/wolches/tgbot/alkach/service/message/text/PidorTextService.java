package io.github.wolches.tgbot.alkach.service.message.text;

import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.Chat;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class PidorTextService implements TextMessageProcessingService {

    private static final String NO_REGEXP = "^([Нн][Ее][Тт]([.?!])*)$";
    private static final String PIDORA_OTVET = "Пидора ответ.";

    @Override
    public boolean isApplicableInternal(Message message, Chat chat, ChatUser user) {
        return isNo(message.getText());
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public String processMessageInternal(Message message, Chat chat, ChatUser user) {
        return PIDORA_OTVET;
    }

    private boolean isNo(String text) {
        return text.equalsIgnoreCase("нет") || text.matches(NO_REGEXP);
    }
}
