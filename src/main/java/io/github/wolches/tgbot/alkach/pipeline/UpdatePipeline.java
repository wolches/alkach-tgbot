package io.github.wolches.tgbot.alkach.pipeline;

import io.github.wolches.tgbot.alkach.handlers.message.step.DefineCommandHandlersStep;
import io.github.wolches.tgbot.alkach.handlers.message.step.DefineTextHandlersStep;
import io.github.wolches.tgbot.alkach.handlers.message.step.HandleCommandStep;
import io.github.wolches.tgbot.alkach.handlers.message.step.HandleTextMessageStep;
import io.github.wolches.tgbot.alkach.pipeline.context.UpdateContext;
import io.github.wolches.tgbot.alkach.pipeline.pipe.Pipeline;
import io.github.wolches.tgbot.alkach.pipeline.pipe.PipelineBuilder;
import io.github.wolches.tgbot.alkach.pipeline.step.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdatePipeline {

    private final RegisterUserMessageStep registerUserMessageStep;
    private final DefineCommandHandlersStep defineCommandHandlersStep;
    private final HandleCommandStep handleCommandStep;
    private final DefineTextHandlersStep defineTextHandlersStep;
    private final HandleTextMessageStep handleTextMessageStep;
    private final SendResponseStep sendResponseStep;

    private Pipeline<Update, UpdateContext> pipeline;

    @PostConstruct
    public void buildPipeline() {
        log.info("#buildPipeline(): Building update processing pipeline");
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
                                .build()))
                        .build()
                        .next(sendResponseStep)
                        .build()))
                .build()
                .build();
        log.info("#buildPipeline(): Successfully built update processing pipeline");
    }

    public void processUpdate(Update update) {
        Optional<String> user = Optional.ofNullable(update.getMessage())
                    .map(Message::getFrom)
                    .map(u -> u.getId() + ":" + u.getUserName());
        log.info("#processUpdate({}): Running the update pipeline, user: [{}]", update.getUpdateId(), user.orElse("N/A"));
        try {
            pipeline.run(update);
        } catch (Throwable e) {
            log.error("#processUpdate({}): An error has occurred during pipeline execution", update.getUpdateId(), e);
        }
    }
}
