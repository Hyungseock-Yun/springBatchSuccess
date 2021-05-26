package com.frame.batch.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TestJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job otherJob() {
        return jobBuilderFactory.get("otherJob")
                .start(otherStep())
                .build();
    }

    @Bean
    public Step otherStep() {
        return stepBuilderFactory.get("otherStep")
                .tasklet(
                        ((contribution, chunkContext) -> {
                          log.info("excuted OtherTasklet !!!!!!!!!!!!!");
                          return RepeatStatus.FINISHED;
                        })
                ).build();
    }
}
