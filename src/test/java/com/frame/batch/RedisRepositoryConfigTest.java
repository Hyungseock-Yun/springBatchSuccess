package com.frame.batch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisRepositoryConfigTest {


    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void testString() {
        final String key="testString";
        final ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();

        stringStringValueOperations.set(key, "1");
//        final String result
    }

}
