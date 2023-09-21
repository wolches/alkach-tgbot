package io.github.wolches.tgbot.alkach.handlers.message.step;

import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.handlers.message.text.TextMessageHandler;
import io.github.wolches.tgbot.alkach.pipeline.context.UpdateContext;
import io.github.wolches.tgbot.alkach.pipeline.step.Step;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
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
        context.add("text_msg_handlers", handlers);
    }

    @Override
    public void reject(UpdateContext context) {

    }
}
