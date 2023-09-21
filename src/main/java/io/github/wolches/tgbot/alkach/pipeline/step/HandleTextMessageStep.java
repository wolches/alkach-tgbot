package io.github.wolches.tgbot.alkach.pipeline.step;

import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.handlers.message.command.CommandHandler;
import io.github.wolches.tgbot.alkach.handlers.message.text.TextMessageHandler;
import io.github.wolches.tgbot.alkach.pipeline.context.UpdateContext;

import java.util.List;

public class HandleTextMessageStep implements Step<UpdateContext> {

    @Override
    public void accept(UpdateContext context) {
        List<TextMessageHandler> textMessageHandlers = context.get("text_msg_handlers", List.class);
        ChatUser chatUser = context.get("msg_chat_user", ChatUser.class);
        textMessageHandlers.stream()
                .map(ch -> ch.handle(context.getMessage(), chatUser.getChat(), chatUser))
                .forEach(txt -> context.add("reply_text", txt));
    }

    @Override
    public void reject(UpdateContext context) {

    }
}
