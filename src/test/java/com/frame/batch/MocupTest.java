package com.frame.batch;

import com.frame.batch.controller.BatchController;
import com.frame.batch.jobs.MediaJob;
import com.frame.batch.service.SampleService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
@RunWith(SpringRunner.class)
@WebMvcTest(BatchController.class)
public class MocupTest {

    /**
     * Mock Mvc 테스트
     */

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JobLauncher jobLauncher;

    @MockBean
    MediaJob mediaJob;

    @MockBean
    SampleService sampleService;

    @Test
    public void apiTest() throws Exception {

//        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
//        info.add("userId", "testUserId");
        mockMvc.perform(get("/batch/test/billing/testUserId"))
                .andExpect(status().is(200))
                .andDo(print());


    }

}
