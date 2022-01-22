package io.github.wolches.tgbot.pesda.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReplyDto {

    private String chatId;
    private Integer replyMessageId;
    private String text;
}
