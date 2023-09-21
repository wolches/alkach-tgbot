package io.github.wolches.tgbot.alkach.pipeline.step;


import io.github.wolches.tgbot.alkach.pipeline.ctx.UpdateContext;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.service.user.ChatUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class RegisterUserMessageStep implements Step<UpdateContext> {

    private final ChatUserService chatUserService;

    @Override
    public void accept(UpdateContext context) {
        Message message = context.getUpdate().getMessage();
        ChatUser chatUser = chatUserService.getChatUserForMessage(message);
        context.add("msg_chat_user", chatUser);
    }

    @Override
    public void reject(UpdateContext context) {

    }
}
