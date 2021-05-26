package com.frame.batch.scheduler;

import com.frame.batch.jobs.MediaJob;
import com.frame.batch.jobs.TestJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final JobLauncher jobLauncher;
    private final MediaJob mediaJob;
    private final TestJob testJob;


//    @Scheduled(fixedDelay = 5 * 1000L)
    public void mediaLiveSchedule() {

        JobExecution execution;

        try {
            execution = jobLauncher.run(mediaJob.mediaLiveJob(), simpleJobParam());
            log.info("Job finished with status : " + execution.getStatus());

        } catch (Exception ex){
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
    }


//    @Scheduled(fixedDelay = 3 * 1000L)
    public void otherSchedule() {

        try {
            JobExecution execution = jobLauncher.run(testJob.otherJob(), simpleJobParam());
            log.info("Job finished with status : " + execution.getStatus());

        } catch (Exception ex){
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private JobParameters simpleJobParam() {
        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter(System.currentTimeMillis()));
        return new JobParameters(confMap);
    }

}
