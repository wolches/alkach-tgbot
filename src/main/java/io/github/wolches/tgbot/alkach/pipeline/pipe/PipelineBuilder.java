package io.github.wolches.tgbot.alkach.pipeline.pipe;

import io.github.wolches.tgbot.alkach.pipeline.step.IfStep;
import io.github.wolches.tgbot.alkach.pipeline.step.IfStepBuilder;
import io.github.wolches.tgbot.alkach.pipeline.step.Step;
import io.github.wolches.tgbot.alkach.pipeline.ctx.Context;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class PipelineBuilder<I, C extends Context> {

    private final Function<I, C> contextInit;
    private final LinkedList<Step<C>> steps;

    public static <I, C extends Context> PipelineBuilder<I, C> start(Function<I, C> contextInit) {
        return new PipelineBuilder<>(contextInit, new LinkedList<>());
    }

    public PipelineBuilder<I, C> next(Step<C> step) {
        steps.add(step);
        return this;
    }

    public IfStepBuilder<I, C> ifExp(Predicate<C> expression) {
        return new IfStepBuilder<>(expression, this);
    }

    public Pipeline<I, C> build() {
        return new Pipeline<>(contextInit, steps);
    }
}
