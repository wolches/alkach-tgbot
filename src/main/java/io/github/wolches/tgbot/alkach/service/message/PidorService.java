package io.github.wolches.tgbot.alkach.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class PidorService implements MessageProcessingService {

    private static final String NO_REGEXP = "^((Н|н)(Е|е)(Т|т)([.?!])*)$";
    private static final String PIDORA_OTVET = "Пидора ответ.";

    @Override
    public boolean isApplicableInternal(Message message) {
        return isNo(message.getText());
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public String processMessageInternal(Message message) {
        return PIDORA_OTVET;
    }

    private boolean isNo(String text) {
        return text.equalsIgnoreCase("нет") || text.matches(NO_REGEXP);
    }
}
