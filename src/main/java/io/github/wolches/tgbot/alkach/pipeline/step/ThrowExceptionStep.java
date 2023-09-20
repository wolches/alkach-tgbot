package io.github.wolches.tgbot.alkach.pipeline.step;

import io.github.wolches.tgbot.alkach.pipeline.ctx.Context;

public class ThrowExceptionStep<C extends Context> implements Step<C> {

    @Override
    public void accept(Context context) {
        throw new RuntimeException();
    }

    @Override
    public void reject(Context context) {

    }
}
