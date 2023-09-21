package io.github.wolches.tgbot.alkach.pipeline;

import io.github.wolches.tgbot.alkach.pipeline.context.UpdateContext;
import io.github.wolches.tgbot.alkach.pipeline.pipe.Pipeline;
import io.github.wolches.tgbot.alkach.pipeline.pipe.PipelineBuilder;
import io.github.wolches.tgbot.alkach.pipeline.step.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
@RequiredArgsConstructor
public class UpdatePipeline {

    private final RegisterUserMessageStep registerUserMessageStep;
    private final DefineCommandHandlersStep defineCommandHandlersStep;
    private final HandleCommandStep handleCommandStep;
    private final DefineTextHandlersStep defineTextHandlersStep;
    private final HandleTextMessageStep handleTextMessageStep;
    private final SendResponseStep sendResponseStep;

    private Pipeline<Update, UpdateContext> pipeline;

    public void buildPipeline() {
        pipeline = PipelineBuilder
                .start((Update upd) -> new UpdateContext(upd))
                .ifExp(UpdateContext::hasMessage)
                .then(new StepPipeline<>(PipelineBuilder
                        .start((UpdateContext u) -> u)
                        .next(registerUserMessageStep)
                        .ifExp(UpdateContext::hasText)
                        .then(new StepPipeline<>(PipelineBuilder
                                .start((UpdateContext u) -> u)
                                .ifExp(UpdateContext::isCommand)
                                .then(new StepPipeline<>(PipelineBuilder
                                        .start((UpdateContext u) -> u)
                                        .next(defineCommandHandlersStep)
                                        .next(handleCommandStep)
                                        .build()))
                                .orElse(new StepPipeline<>(PipelineBuilder
                                        .start((UpdateContext u) -> u)
                                        .next(defineTextHandlersStep)
                                        .next(handleTextMessageStep)
                                        .build()))
                                .build()
                                .next(sendResponseStep)
                                .build()))
                        .build()
                        .build()))
                .build()
                .build();
    }


    public void processUpdate(Update update) {
        pipeline.run(update);
    }
}
