package com.frame.batch.controller;

import com.frame.batch.dao.billing.entity.UserProfile;
import com.frame.batch.dao.dream.entity.NewOne;
import com.frame.batch.jobs.MediaJob;
import com.frame.batch.service.SampleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/batch")
@Api(value = "배치 리스트 Controller")
public class BatchController {

    private final JobLauncher jobLauncher;
    private final MediaJob mediaJob;

    private final SampleService service;

    @ApiOperation(value="테스트호출")
    @GetMapping("")
    public String testApi() {
        return "success";
    }

    @ApiOperation(value="배치 호출 테스트 OneTime")
    @GetMapping("/test")
    public void oneTimeBatchTest() {

        JobExecution execution = null;
        try {
            execution = jobLauncher.run(mediaJob.mediaLiveJob(), simpleJobParam());
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
        log.info("Job finished with status : " + execution.getStatus());

        log.info("Controller Batch test!!!~~~~~");
    }

    @ApiOperation(value = "샘플 DAO TEST >> billing DB")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자아이디", required = true, dataType = "string", paramType = "path", defaultValue = "testUserId")
    })
    @GetMapping("/test/billing/{userId}")
    public ResponseEntity<UserProfile> billingTest(@PathVariable String userId) throws Exception {
        UserProfile userProfile = service.selectUser(userId);
        return ResponseEntity.ok(userProfile);
    }

    @ApiOperation(value = "샘플 DAO TEST >> dream DB")
    @GetMapping("/test/dream")
    public ResponseEntity<List<NewOne>> dreamTest() throws Exception {
        List<NewOne> newOnes = service.selectNews();


        log.trace("Trace Level 테스트");
        log.debug("DEGUB Level 테스트");
        log.info("INFO LEVEL 테스트");
        log.warn("WARN Level 테스트");
        log.error("ERROR Level 테스트");

        return ResponseEntity.ok(newOnes);
    }




    private JobParameters simpleJobParam() {
        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter(System.currentTimeMillis()));
        return new JobParameters(confMap);
    }

}
