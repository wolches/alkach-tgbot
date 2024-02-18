package io.github.wolches.tgbot.alkach.pipeline.common.step;

import io.github.wolches.tgbot.alkach.pipeline.common.context.Context;


/**
 * @author wolches
 * @param <C> Context
 * Remember, step in pipeline has no state.
 */
@FunctionalInterface
public interface Step<C extends Context> {

    void accept(C context);

    default void reject(C context) {

    }
}
