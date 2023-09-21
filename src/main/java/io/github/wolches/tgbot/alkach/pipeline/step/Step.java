package io.github.wolches.tgbot.alkach.pipeline.step;

import io.github.wolches.tgbot.alkach.pipeline.context.Context;

public interface Step<C extends Context> {

    void accept(C context);

    void reject(C context);
}
