package io.github.wolches.tgbot.alkach.updpipe;

import io.github.wolches.tgbot.alkach.pipeline.pipe.Pipeline;
import io.github.wolches.tgbot.alkach.pipeline.pipe.PipelineBuilder;
import io.github.wolches.tgbot.alkach.pipeline.step.DoNothingStep;
import io.github.wolches.tgbot.alkach.pipeline.step.Step;
import io.github.wolches.tgbot.alkach.pipeline.step.StepPipeline;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdatePipeline {



    public Pipeline<Update, TgUpdCtx> pipeline() {
        Pipeline test = PipelineBuilder.start((Update u) -> new TgUpdCtx(u))
                .ifExp(TgUpdCtx::hasMessage)
                .then(new StepPipeline<>(
                        PipelineBuilder.start((TgUpdCtx c) -> c)
                                .next(new Step<>() {
                                    @Override
                                    public void accept(TgUpdCtx context) {
                                        System.out.println("test");
                                    }

                                    @Override
                                    public void reject(TgUpdCtx context) {

                                    }
                                })
                                .build()
                    ))
                    .orElse(new DoNothingStep())
                    .build()
                .build();
        return test;
    }
}
