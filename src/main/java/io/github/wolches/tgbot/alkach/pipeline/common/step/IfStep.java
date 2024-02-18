package io.github.wolches.tgbot.alkach.pipeline.common.step;

import io.github.wolches.tgbot.alkach.pipeline.common.context.Context;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Predicate;

@Slf4j
@RequiredArgsConstructor
public class IfStep<C extends Context> implements Step<C> {

    private final Predicate<C> expression;

    private final Step<C> ifTrueStep;

    @Setter
    private Step<C> elseStep;

    @Override
    public void accept(C context) {
        boolean result = expression.test(context);
        log.debug("#accept({}): Performing ifStep, expression evaluated into {} from context\n{}",
                  context.getClass().getSimpleName(), result, context);
        if (result) {
            ifTrueStep.accept(context);
        } else if (hasElse()) {
            elseStep.accept(context);
        }
    }

    @Override
    public void reject(C context) {
        boolean result = expression.test(context);
        if (result) {
            ifTrueStep.reject(context);
        } else if (hasElse()) {
            elseStep.reject(context);
        }
    }

    private boolean hasElse() {
        return elseStep != null;
    }

}
