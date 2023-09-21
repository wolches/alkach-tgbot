package io.github.wolches.tgbot.alkach.pipeline.context;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@Data
public class UpdateContext extends AbstractContext {

    private final Update update;

    public boolean hasMessage() {
        return update.hasMessage();
    }

    public boolean hasText() {
        Message message = update.getMessage();
        return message.getText() != null
               && !message.getText().equals("");
    }

    public Message getMessage() {
        return update.getMessage();
    }

    public boolean isCommand() {
        return update.getMessage().getText().startsWith("/");
    }

}
