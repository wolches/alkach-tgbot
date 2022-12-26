package io.github.wolches.tgbot.alkach.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class UpdateProcessingResultDto {

    private Optional<ReplyMessageDto> messageProcessingResultDto;
}
