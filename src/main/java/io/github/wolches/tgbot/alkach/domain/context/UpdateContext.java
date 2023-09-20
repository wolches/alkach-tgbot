package io.github.wolches.tgbot.alkach.domain.context;

import io.github.wolches.tgbot.alkach.pipeline.ctx.AbstractContext;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@Data
public class UpdateContext extends AbstractContext {

    private final Update update;

}
