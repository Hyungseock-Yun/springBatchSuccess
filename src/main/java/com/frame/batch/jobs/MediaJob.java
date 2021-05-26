package com.frame.batch.jobs;

import com.frame.batch.tasklets.media.MediaLiveTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MediaJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job mediaLiveJob() {

        return jobBuilderFactory.get("mediaLiveJob")
                .start(mediaLiveStepOne())
                .next(mediaLiveStepTwo())
                .build();

    }

    @Bean
    public Step mediaLiveStepOne() {
        return stepBuilderFactory.get("mediaLiveStepOne")
                .tasklet(new MediaLiveTasklet())
                .build();
    }

    @Bean
    public Step mediaLiveStepTwo() {
        return stepBuilderFactory.get("mediaLiveStepTwo")
                .tasklet(new MediaLiveTasklet())
                .build();
    }



}
