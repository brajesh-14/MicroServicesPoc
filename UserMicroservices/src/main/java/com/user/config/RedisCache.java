package com.user.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
@Slf4j
public class RedisCache {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //Redis server se connection establish karta hai
        template.setConnectionFactory(factory);

       // Redis key ko human readable string banata hai
        template.setKeySerializer(new StringRedisSerializer());
        //Kya karta hai: Java Object → JSON & JSON → Java Object
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        //Redis HASH ke andar ke keys ko readable banata hai
        template.setHashKeySerializer(new StringRedisSerializer());
        //Hash ke values ko JSON me convert karta hai
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        //Spring ko bolta hai:“Template fully configured hai, ab use ready hai”
        template.afterPropertiesSet();
        return template;
    }

    // For counters, rate limiting, OTP, JWT blacklist(not in use right now)
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory){
        return new StringRedisTemplate(factory);
    }


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {

        RedisCacheConfiguration config =
                RedisCacheConfiguration.defaultCacheConfig()
                        .disableCachingNullValues()
                        .serializeKeysWith(
                                RedisSerializationContext.SerializationPair
                                        .fromSerializer(new StringRedisSerializer()))
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair
                                        .fromSerializer(new GenericJackson2JsonRedisSerializer()))
                        .entryTtl(Duration.ofMinutes(10)); // default TTL

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }


}
