package io.github.wolches.tgbot.alkach.updpipe;

import io.github.wolches.tgbot.alkach.pipeline.ctx.AbstractContext;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Update;

@Data
public class TgUpdCtx extends AbstractContext {

    private final Update originalUpdate;

    public boolean hasMessage() {
        return originalUpdate.hasMessage();
    }
}
