package io.github.wolches.tgbot.alkach.controller;

import io.github.wolches.tgbot.alkach.domain.dto.UpdateProcessingResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UpdateController {

    private final MessageController messageController;

    public Optional<UpdateProcessingResultDto> processUpdate(Update update) {
        UpdateProcessingResultDto.UpdateProcessingResultDtoBuilder builder = UpdateProcessingResultDto.builder();
        builder.messageProcessingResultDto(update.hasMessage() ? messageController.processMessageUpdate(update) : Optional.empty());
        return Optional.of(builder.build());
    }
}
