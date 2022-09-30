package io.github.wolches.tgbot.alkach.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultDto {

    private String chatId;
    private Integer replyMessageId;
    private String replyText;
}
