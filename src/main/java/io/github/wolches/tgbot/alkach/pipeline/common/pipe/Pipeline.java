package io.github.wolches.tgbot.alkach.pipeline.common.pipe;

import io.github.wolches.tgbot.alkach.pipeline.common.context.Context;
import io.github.wolches.tgbot.alkach.pipeline.common.step.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.function.Function;

/**
 * Pipeline has no state
 * @param <I> Input
 * @param <C> Context
 */
@RequiredArgsConstructor
@Slf4j
public class Pipeline<I, C extends Context> {

    private final String id;
    private final Function<I, C> contextInit;
    private final LinkedList<Step<C>> nextSteps;
    private final LinkedList<Step<C>> pastSteps = new LinkedList<>();

    public void run(I input) {
        log.info("#run {} ({}): {} steps next", id, input.getClass().getSimpleName(), nextSteps.size());
        C context = contextInit.apply(input);
        try {
            // TODO Refactor this somehow, mb iterator?
            /** Cause further calling nextSteps.pop() will reduce the queue size */
            int nextStepsCount = nextSteps.size();
            for (int i = 0; i < nextStepsCount; i++) {
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
        // TODO Refactor this somehow, mb iterator?
        /** Cause further calling pastSteps.pop() will reduce the queue size */
        int pastStepsTotal = pastSteps.size();
        for (int i = 0; i < pastStepsTotal; i++) {
            Step<C> prevstep = pastSteps.pop();
            prevstep.reject(context);
        }
    }

    private void processStep(Step<C> step, C context) {
        try {
            log.debug("#processStep({}, {}): processing step:\n{}",
                      step.getClass().getSimpleName(), context.getClass().getSimpleName(), step);
            step.accept(context);
        } catch (Throwable e) {
            log.error("An error has occured when {} step execution in pipeline {}",
                      step.getClass().getSimpleName(), this.getClass().getSimpleName(), e);
            step.reject(context);
            throw e;
        }
    }
}
