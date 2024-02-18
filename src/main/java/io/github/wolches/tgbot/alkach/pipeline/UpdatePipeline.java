package io.github.wolches.tgbot.alkach.pipeline;

import io.github.wolches.tgbot.alkach.handlers.message.step.DefineCommandHandlersStep;
import io.github.wolches.tgbot.alkach.handlers.message.step.DefineTextHandlersStep;
import io.github.wolches.tgbot.alkach.handlers.message.step.HandleCommandStep;
import io.github.wolches.tgbot.alkach.handlers.message.step.HandleTextMessageStep;
import io.github.wolches.tgbot.alkach.pipeline.common.pipe.Pipeline;
import io.github.wolches.tgbot.alkach.pipeline.common.pipe.PipelineBuilder;
import io.github.wolches.tgbot.alkach.pipeline.common.step.StepPipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

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

    public Pipeline<Update, UpdateContext> buildPipeline() {
        log.debug("#buildPipeline(): Building update processing pipeline");
        var pipeline = PipelineBuilder
                .start((Update upd) -> new UpdateContext(upd))
                .id("updatePipeline")
                .ifExp(UpdateContext::hasMessage)
                .then(new StepPipeline<>(PipelineBuilder
                        .start((UpdateContext u) -> u)
                        .id("registerUserPipeline")
                        .next(registerUserMessageStep)
                        .ifExp(UpdateContext::hasText)
                        .then(new StepPipeline<>(PipelineBuilder
                                .start((UpdateContext u) -> u)
                                .id("handleMessagePipeline")
                                .ifExp(UpdateContext::isCommand)
                                .then(new StepPipeline<>(PipelineBuilder
                                        .start((UpdateContext u) -> u)
                                        .id("handleCommandPipeline")
                                        .next(defineCommandHandlersStep)
                                        .next(handleCommandStep)
                                        .build()))
                                .orElse(new StepPipeline<>(PipelineBuilder
                                        .start((UpdateContext u) -> u)
                                        .id("handleNonCommandPipeline")
                                        .next(defineTextHandlersStep)
                                        .next(handleTextMessageStep)
                                        .build()))
                                .build()
                                .build()))
                        .build()
                        .next(sendResponseStep)
                        .build()))
                .build()
                .ifExp(UpdateContext::hasCallbackQuery)
                .then(new StepPipeline<>(PipelineBuilder
                        .start((UpdateContext u) -> u)
                        .build()))
                .build()
                .build();
        log.debug("#buildPipeline(): Successfully built update processing pipeline");
        return pipeline;
    }

    public void processUpdate(Update update) {
        Optional<String> user = Optional.ofNullable(update.getMessage())
                    .map(Message::getFrom)
                    .map(u -> u.getId() + ":" + u.getUserName());
        log.info("#processUpdate({}): Running the update pipeline, user: [{}]", update.getUpdateId(), user.orElse("N/A"));
        try {
            Pipeline<Update, UpdateContext> pipeline = buildPipeline();
            pipeline.run(update);
        } catch (Throwable e) {
            log.error("#processUpdate({}): An error has occurred during pipeline execution", update.getUpdateId(), e);
        }
    }
}
