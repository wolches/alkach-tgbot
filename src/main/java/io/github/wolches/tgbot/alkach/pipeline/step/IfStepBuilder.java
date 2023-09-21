package io.github.wolches.tgbot.alkach.pipeline.step;

import io.github.wolches.tgbot.alkach.pipeline.context.Context;
import io.github.wolches.tgbot.alkach.pipeline.pipe.PipelineBuilder;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

@RequiredArgsConstructor
public class IfStepBuilder<I, C extends Context> {

    private final Predicate<C> expression;
    private final PipelineBuilder<I, C> parentBuilder;

    private Step<C> stepIf;
    private Step<C> stepElse;

    public IfStepBuilder<I, C> then(Step<C> step) {
        stepIf = step;
        return this;
    }

    public IfStepBuilder<I, C> orElse(Step<C> step) {
        stepElse = step;
        return this;
    }

    public PipelineBuilder<I, C> build() {
        if (stepIf == null) throw new IllegalStateException();
        IfStep<C> step = new IfStep<>(expression, stepIf);
        if (stepElse != null) step.setElseStep(stepElse);
        parentBuilder.next(step);
        return parentBuilder;
    }
}
