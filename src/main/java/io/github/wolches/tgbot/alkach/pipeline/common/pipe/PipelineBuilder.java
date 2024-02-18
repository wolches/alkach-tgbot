package io.github.wolches.tgbot.alkach.pipeline.common.pipe;

import io.github.wolches.tgbot.alkach.pipeline.common.context.Context;
import io.github.wolches.tgbot.alkach.pipeline.common.step.IfStepBuilder;
import io.github.wolches.tgbot.alkach.pipeline.common.step.Step;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.Predicate;

@AllArgsConstructor
public class PipelineBuilder<I, C extends Context> {

    private String id;
    private final Function<I, C> contextInit;
    private final LinkedList<Step<C>> steps;

    public static <I, C extends Context> PipelineBuilder<I, C> start(Function<I, C> contextInit) {
        return new PipelineBuilder<>("", contextInit, new LinkedList<>());
    }

    public static <I, C extends Context> PipelineBuilder<I, C> start(String name, Function<I, C> contextInit) {
        return new PipelineBuilder<>(name, contextInit, new LinkedList<>());
    }

    public PipelineBuilder<I, C> next(Step<C> step) {
        steps.add(step);
        return this;
    }

    public PipelineBuilder<I, C> id(String id) {
        this.id = id;
        return this;
    }

    public IfStepBuilder<I, C> ifExp(Predicate<C> expression) {
        return new IfStepBuilder<>(expression, this);
    }

    public Pipeline<I, C> build() {
        return new Pipeline<>(id, contextInit, steps);
    }

    public Pipeline<I, C> build(String id) {
        id = id.isEmpty() ? this.id : id;
        return new Pipeline<>(id, contextInit, steps);
    }
}
