package io.github.wolches.tgbot.alkach.service.update;

import io.github.wolches.tgbot.alkach.domain.dto.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateService {

    private final MessageUpdateService messageUpdateService;

    public Optional<ReplyDto> processUpdate(Update update) {
        if (update.hasMessage()) {
            return messageUpdateService.processUpdate(update);
        }
        return Optional.empty();
    }
}
