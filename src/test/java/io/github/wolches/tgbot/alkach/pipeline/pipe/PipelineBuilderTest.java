package io.github.wolches.tgbot.alkach.pipeline.pipe;

import io.github.wolches.tgbot.alkach.pipeline.context.TestContext;
import io.github.wolches.tgbot.alkach.pipeline.step.Step;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

class PipelineBuilderTest {

    @Test
    public void testCreateAndRunPipeline() {
        // Given
        Step<TestContext> mockStep = (Step<TestContext>) Mockito.mock(Step.class);
        // When
        Pipeline<Object, TestContext> pipeline = PipelineBuilder.start(i -> new TestContext())
                .next(mockStep)
                .build();
        pipeline.run(new Object());
        // Then
        Mockito.verify(mockStep, Mockito.times(1)).accept(Mockito.any(TestContext.class));
    }

    @Test
    public void testIfStep() {
        // Given
        Step<TestContext> mockStep = (Step<TestContext>) Mockito.mock(Step.class);
        Step<TestContext> mockStepIf = (Step<TestContext>) Mockito.mock(Step.class);
        Step<TestContext> mockStepElse = (Step<TestContext>) Mockito.mock(Step.class);
        final boolean condition = new Random().nextBoolean();
        // When
        Pipeline<Object, TestContext> pipeline = PipelineBuilder.start(i -> new TestContext())
                .next(mockStep)
                .ifExp(tc -> condition)
                    .then(mockStepIf)
                    .orElse(mockStepElse)
                    .build()
                .build();
        pipeline.run(new Object());
        // Then
        Mockito.verify(mockStep).accept(Mockito.any(TestContext.class));
        if (condition) {
            Mockito.verify(mockStepIf, Mockito.times(1)).accept(Mockito.any(TestContext.class));
            Mockito.verify(mockStepElse, Mockito.never()).accept(Mockito.any(TestContext.class));
        } else {
            Mockito.verify(mockStepIf, Mockito.never()).accept(Mockito.any(TestContext.class));
            Mockito.verify(mockStepElse, Mockito.times(1)).accept(Mockito.any(TestContext.class));
        }
    }

    @Test
    public void classNameTest() {
        Class<TestContext> clazz = TestContext.class;
        System.out.println(clazz.getName());
        System.out.println(clazz.getSimpleName());
        System.out.println(clazz.getCanonicalName());
        System.out.println(clazz.getTypeName());
        System.out.println(clazz.getPackageName());
    }

}