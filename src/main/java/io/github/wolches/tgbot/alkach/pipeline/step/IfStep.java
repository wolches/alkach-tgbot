package io.github.wolches.tgbot.alkach.pipeline.step;

import io.github.wolches.tgbot.alkach.pipeline.ctx.Context;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.function.Predicate;

@RequiredArgsConstructor
public class IfStep<C extends Context> implements Step<C> {

    private final Predicate<C> expression;

    private final Step<C> ifTrueStep;

    @Setter
    private Step<C> elseStep;

    private Boolean result;

    @Override
    public void accept(C context) {
        result = expression.test(context);
        if (result != null) {
            if (result) {
                ifTrueStep.accept(context);
            } else if (hasElse()) {
                elseStep.accept(context);
            }
        }
    }

    @Override
    public void reject(C context) {
        if (result != null) {
            if (result) {
                ifTrueStep.reject(context);
            } else if (hasElse()) {
                elseStep.reject(context);
            }
        }
    }

    private boolean hasElse() {
        return elseStep != null;
    }

}
