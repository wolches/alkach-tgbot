package io.github.wolches.tgbot.alkach.pipeline.step;

import io.github.wolches.tgbot.alkach.pipeline.ctx.Context;
import io.github.wolches.tgbot.alkach.pipeline.pipe.Pipeline;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StepPipeline<C extends Context> implements Step<C> {

    private final Pipeline<C, C> internal;

    @Override
    public void accept(C context) {
        internal.run(context);
    }

    @Override
    public void reject(C context) {
        internal.rejectAllPrevSteps(context);
    }
}
