package io.github.wolches.tgbot.pesda.bot;

import io.github.wolches.tgbot.pesda.domain.dto.ReplyDto;
import io.github.wolches.tgbot.pesda.service.MessageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class BotInstance extends TelegramLongPollingBot {

    private final String botToken;
    private final String botUsername;

    @Setter(onMethod_={@Autowired})
    private MessageService messageService;

    @Override
    public void onUpdateReceived(Update update) {
        Optional<ReplyDto> reply = messageService.processUpdate(update);
        reply.ifPresent(
                r -> {
                    try {
                        SendMessage msg = new SendMessage();
                        msg.setChatId(r.getChatId());
                        msg.setParseMode("Markdown");
                        msg.setReplyToMessageId(r.getReplyMessageId());
                        msg.setText(r.getText());
                        execute(msg);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}
