package io.github.wolches.tgbot.alkach.handlers.message.step;

import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.handlers.message.text.TextMessageHandler;
import io.github.wolches.tgbot.alkach.pipeline.UpdateContext;
import io.github.wolches.tgbot.alkach.pipeline.common.step.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class DefineTextHandlersStep implements Step<UpdateContext> {

    private final List<TextMessageHandler> textMessageHandlers;
    @Override
    public void accept(UpdateContext context) {
        ChatUser chatUser = context.get("msg_chat_user", ChatUser.class);
        List<TextMessageHandler> handlers = textMessageHandlers
                .stream()
                .filter(handler -> handler.isApplicable(context.getUpdate().getMessage(), chatUser.getChat(), chatUser))
                .collect(Collectors.toList());
        Object[] handlersNames = handlers.stream().map((TextMessageHandler ch) -> ch.getClass().getName()).collect(Collectors.toList()).toArray();
        log.debug("#accept({}): Found applicable handlers {}", context.getUpdate().getUpdateId(), handlersNames);
        context.add("text_msg_handlers", handlers);
    }

    @Override
    public void reject(UpdateContext context) {

    }
}
