package io.github.wolches.tgbot.alkach.pipeline.step;

import io.github.wolches.tgbot.alkach.pipeline.ctx.Context;

public class DoNothingStep implements Step {

    @Override
    public void accept(Context context) {
        //System.out.println("Do nothing");
    }

    @Override
    public void reject(Context context) {

    }
}