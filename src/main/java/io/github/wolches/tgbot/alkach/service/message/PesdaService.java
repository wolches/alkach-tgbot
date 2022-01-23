package io.github.wolches.tgbot.alkach.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class PesdaService implements MessageProcessingService {

    private static final String YES_REGEXP = "^((Д|д)(А|а)([.?!])*)$";
    private static final String PESDA = "Пизда.";

    @Override
    public boolean isApplicableInternal(Message message) {
        return isYes(message.getText());
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public String processMessageInternal(Message message) {
        return PESDA;
    }

    private boolean isYes(String text) {
        return text.equalsIgnoreCase("да") || text.matches(YES_REGEXP);
    }
}
