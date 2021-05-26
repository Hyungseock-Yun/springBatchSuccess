package com.frame.batch.dao.billing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frame.batch.dao.billing.entity.UserProfile;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisDao {

    private final RedisConnectionFactory redisConnectionFactory;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, byte[]> messagePackRedisTemplate;
    private final ObjectMapper messagePackObjectMapper;

    private static final String BLOCKED_USER_KEY = "BLOCKED_USERS:${USERID}";
    private static final String USER_KEY = "USERS:${USERID}";


    public RedisDao(
            @Qualifier("redisConnectionFactory") RedisConnectionFactory redisConnectionFactory,
            @Qualifier("stringRedisTemplate") StringRedisTemplate stringRedisTemplate,
            @Qualifier("messagePackRedisTemplate") RedisTemplate<String, byte[]> messagePackRedisTemplate,
            @Qualifier("messagePackObjectMapper") ObjectMapper messagePackObjectMapper
    ) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.stringRedisTemplate = stringRedisTemplate;
        this.messagePackRedisTemplate = messagePackRedisTemplate;
        this.messagePackObjectMapper = messagePackObjectMapper;
    }

    public boolean isUserBlocked(String userId) {
        String key = StringSubstitutor.replace(
                BLOCKED_USER_KEY, Map.of("USERID",userId)
        );
        boolean hasKey = stringRedisTemplate.hasKey(key);
        return Objects.requireNonNullElse(hasKey, false);
    }

    public long getUserBlockedSecondsLeft(String userId) {
        String key = StringSubstitutor.replace(
                BLOCKED_USER_KEY,
                Map.of("USERID", userId)
        );
        Long secondsLeft = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        return Objects.requireNonNullElse(secondsLeft, 0L);
    }

    public void setUserBlocked(String userId) {
        String key = StringSubstitutor.replace(
                BLOCKED_USER_KEY,
                Map.of("USERID", userId)
        );
        stringRedisTemplate.opsForValue().set(key, StringUtils.EMPTY, 5, TimeUnit.MINUTES);
    }

    public void deleteUserBlocked(String userId) {
        String key = StringSubstitutor.replace(
          BLOCKED_USER_KEY,
          Map.of("USERID", userId)
        );
        stringRedisTemplate.delete(key);
    }

    public void setUser(UserProfile userProfile) throws JsonProcessingException {
        String key = StringSubstitutor.replace(
                USER_KEY,
                Map.of("USERID", userProfile.getUserId())
        );
        byte[] message = messagePackObjectMapper.writeValueAsBytes(userProfile);
        messagePackRedisTemplate.opsForValue().set(key, message, 1, TimeUnit.HOURS);
    }

    public UserProfile getUser(String userId) throws IOException {
        String key = StringSubstitutor.replace(
                USER_KEY,
                Map.of("USERID", userId)
        );
        byte[] message = messagePackRedisTemplate.opsForValue().get(key);
        if(message == null)
            return null;

        return messagePackObjectMapper.readValue(message, UserProfile.class);

    }

    public void deleteUser(String userId) throws Exception {
        String key = StringSubstitutor.replace(
                USER_KEY,
                Map.of("USERID", userId)
        );

        messagePackRedisTemplate.delete(key);
    }

    public List<String> getAllUsers() {
        String key = StringSubstitutor.replace(USER_KEY, Map.of("USERID", "*"));

        RedisConnection redisConnection = redisConnectionFactory.getConnection();
        ScanOptions options = ScanOptions.scanOptions().count(50).match(key).build();

        List<String> users = new ArrayList<>();
        Cursor<byte[]> cursor = redisConnection.scan(options);

        while (cursor.hasNext()) {
            String user = StringUtils.replace(new String(cursor.next()), "USERS", "");
            users.add(user);
        }

        return users;
    }


}
