package io.github.wolches.tgbot.alkach.pipeline.step;

import io.github.wolches.tgbot.alkach.bot.BotProxyService;
import io.github.wolches.tgbot.alkach.pipeline.context.UpdateContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendResponseStep implements Step<UpdateContext> {

    private final BotProxyService botService;

    // TODO: ReplyMessageDto usage
    @Override
    public void accept(UpdateContext context) {
        try {
            List<String> replies = (List<String>) context.get("reply_text", List.class);
            for (String reply : replies) {
                SendMessage msg = new SendMessage();
                Message message = context.getMessage();
                msg.setChatId(message.getChatId().toString());
                msg.setParseMode("Markdown");
                msg.setReplyToMessageId(message.getMessageId());
                msg.setText(reply);
                botService.execute(msg);
            }
        } catch (TelegramApiException e) {
            log.error("Error sending message", e);
        }
    }

    @Override
    public void reject(UpdateContext context) {

    }
}
