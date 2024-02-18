package io.github.wolches.tgbot.alkach.pipeline.common.step;

import io.github.wolches.tgbot.alkach.pipeline.common.context.Context;
import io.github.wolches.tgbot.alkach.pipeline.common.pipe.PipelineBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
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
        logDebugMessage();
        if (stepIf == null) throw new IllegalStateException();
        IfStep<C> step = new IfStep<>(expression, stepIf);
        if (stepElse != null) step.setElseStep(stepElse);
        parentBuilder.next(step);
        return parentBuilder;
    }

    private void logDebugMessage() { // TODO: Class names utils for null-safe calls
        String elseClass = Optional.ofNullable(stepElse).map(se -> se.getClass().getSimpleName()).orElse("N/A");
        log.debug("#build(): Creating ifStep from steps {} else {}",
                  stepIf.getClass().getSimpleName(), elseClass);
    }
}
