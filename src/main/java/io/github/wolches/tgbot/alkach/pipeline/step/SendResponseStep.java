package io.github.wolches.tgbot.alkach.pipeline.step;

import io.github.wolches.tgbot.alkach.bot.BotProxyService;
import io.github.wolches.tgbot.alkach.pipeline.context.UpdateContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@RequiredArgsConstructor
public class SendResponseStep implements Step<UpdateContext> {

    private final BotProxyService botService;

    @Override
    public void accept(UpdateContext context) {
        try {
            SendMessage msg = new SendMessage();
            Message message = context.getMessage();
            msg.setChatId(message.getChatId().toString());
            msg.setParseMode("Markdown");
            msg.setReplyToMessageId(message.getMessageId());
            msg.setText(context.get("reply_text", String.class));
            botService.execute(msg);
        } catch (TelegramApiException e) {
            log.error("Error sending message", e);
        }
    }

    @Override
    public void reject(UpdateContext context) {

    }
}
