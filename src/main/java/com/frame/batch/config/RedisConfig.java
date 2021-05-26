package com.frame.batch.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Configuration
@EnableRedisRepositories
public class RedisConfig {

    private final Environment env;
    private final RedisProperties redisProperties;

    @Bean("redisConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory() {

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(env.getProperty("REDIS_HOST"));
        config.setPort(Integer.parseInt(env.getProperty("REDIS_PORT")));
        config.setDatabase(Integer.parseInt(env.getProperty("REDIS_DATABASE")));
        config.setPassword(env.getProperty("REDIS_PASSWORD"));

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(Long.parseLong(env.getProperty("REDIS_TIMEOUT"))))
                .build();

        return new LettuceConnectionFactory(config, clientConfig);
    }

    @Bean("redisTemplate")
    public RedisTemplate<?, ?> redisTemplate(@Qualifier("redisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean("stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(@Qualifier("redisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {

        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean("messagePackRedisTemplate")
    public RedisTemplate<String, byte[]> messagePackRedisTemplate(@Qualifier("redisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setEnableDefaultSerializer(false);

        return template;
    }

    @Bean("messagePackObjectMapper")
    public ObjectMapper messagePackObjectMapper() {
        return new ObjectMapper(new MessagePackFactory())
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


}
