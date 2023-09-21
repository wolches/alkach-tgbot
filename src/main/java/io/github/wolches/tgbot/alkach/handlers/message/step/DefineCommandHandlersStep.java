package io.github.wolches.tgbot.alkach.handlers.message.step;

import io.github.wolches.tgbot.alkach.pipeline.context.UpdateContext;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.handlers.message.command.CommandHandler;
import io.github.wolches.tgbot.alkach.pipeline.step.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class DefineCommandHandlersStep implements Step<UpdateContext> {

    private final List<CommandHandler> commandHandlers;
    @Override
    public void accept(UpdateContext context) {
        ChatUser chatUser = context.get("msg_chat_user", ChatUser.class);
        List<CommandHandler> handlers = commandHandlers
                .stream()
                .filter(handler -> handler.isApplicable(context.getUpdate().getMessage(), chatUser.getChat(), chatUser))
                .collect(Collectors.toList());
        Object[] handlersNames = handlers.stream().map((CommandHandler ch) -> ch.getClass().getName()).collect(Collectors.toList()).toArray();
        log.debug("#accept({}): Found applicable handlers {}", context.getUpdate().getUpdateId(), handlersNames);
        context.add("command_handlers", handlers);
    }

    @Override
    public void reject(UpdateContext context) {

    }
}
