package io.github.wolches.tgbot.alkach.handlers.message.step;

import io.github.wolches.tgbot.alkach.pipeline.context.UpdateContext;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.handlers.message.command.CommandHandler;
import io.github.wolches.tgbot.alkach.pipeline.step.Step;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HandleCommandStep implements Step<UpdateContext> {

    @Override
    public void accept(UpdateContext context) {
        List<CommandHandler> commandHandlers = context.get("command_handlers", List.class);
        ChatUser chatUser = context.get("msg_chat_user", ChatUser.class);
        commandHandlers.stream()
                .map(ch -> ch.handle(context.getMessage(), chatUser.getChat(), chatUser))
                .forEach(txt -> context.add("reply_text", txt));
    }

    @Override
    public void reject(UpdateContext context) {

    }
}
