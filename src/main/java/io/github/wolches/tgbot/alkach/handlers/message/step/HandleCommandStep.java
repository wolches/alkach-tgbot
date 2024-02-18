package io.github.wolches.tgbot.alkach.handlers.message.step;

import io.github.wolches.tgbot.alkach.pipeline.UpdateContext;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.handlers.message.command.CommandHandler;
import io.github.wolches.tgbot.alkach.pipeline.common.step.Step;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HandleCommandStep implements Step<UpdateContext> {

    @Override
    public void accept(UpdateContext context) {
        List<CommandHandler> commandHandlers = context.get("command_handlers", List.class);
        ChatUser chatUser = context.get("msg_chat_user", ChatUser.class);
        List<String> responses = commandHandlers.stream()
                .map(ch -> ch.handle(context.getMessage(), chatUser.getChat(), chatUser))
                .collect(Collectors.toList());
        context.add("reply_text", responses);
    }

    @Override
    public void reject(UpdateContext context) {

    }
}
