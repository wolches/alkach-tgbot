package io.github.wolches.tgbot.alkach.pipeline.common.step;

import io.github.wolches.tgbot.alkach.pipeline.common.context.Context;
import org.springframework.stereotype.Component;

@Component
public class DoNothingStep implements Step {

    @Override
    public void accept(Context context) {
        //System.out.println("Do nothing");
    }

    @Override
    public void reject(Context context) {

    }
}
