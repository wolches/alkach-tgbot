package io.github.wolches.tgbot.alkach.pipeline.pipe;

import io.github.wolches.tgbot.alkach.pipeline.step.Step;
import io.github.wolches.tgbot.alkach.pipeline.context.Context;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
public class Pipeline<I, C extends Context> {

    private final Function<I, C> contextInit;
    private final LinkedList<Step<C>> nextSteps;
    private final LinkedList<Step<C>> pastSteps = new LinkedList<>();

    public void run(I input) {
        C context = contextInit.apply(input);
        try {
            for (int i = 0; i < nextSteps.size(); i++) {
                Step<C> step = nextSteps.pop();
                processStep(step, context);
                pastSteps.push(step);
            }
        } catch (Throwable e) {
            log.error("An error has occured", e);
            rejectAllPrevSteps(context);
            throw e;
        }

    }

    public void rejectAllPrevSteps(C context) {
        for (int i = 0; i < pastSteps.size(); i++) {
            Step<C> prevstep = pastSteps.pop();
            prevstep.reject(context);
        }
    }

    private void processStep(Step<C> step, C context) {
        try {
            step.accept(context);
        } catch (Throwable e) {
            log.error("An error has occured when {} step execution in pipeline {}", step.getClass(), this.getClass(), e);
            step.reject(context);
            throw e;
        }
    }
}
