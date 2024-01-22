package com.example.spring_redis.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {
    
    public static final String CACHE1 = "CACHE1";
    public static final String CACHE2 = "CACHE2";
    
    @AllArgsConstructor
    @Getter
    public static class CacheProperty {
        private String name;
        private Integer ttl;
    }
    
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        PolymorphicTypeValidator pvt = BasicPolymorphicTypeValidator.builder()
            .allowIfBaseType(Object.class)
            .build();
        
        var objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule())
            .activateDefaultTyping(pvt, ObjectMapper.DefaultTyping.NON_FINAL)
            .disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        
        List<CacheProperty> properties = List.of(
            new CacheProperty(CACHE1, 300),
            new CacheProperty(CACHE2, 30)
        );
        
        return (builder -> {
            properties.forEach(i -> {
                builder.withCacheConfiguration(i.getName(), RedisCacheConfiguration
                    .defaultCacheConfig()
                    .disableCachingNullValues()
                    .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)))
                    .entryTtl(Duration.ofSeconds(i.getTtl())));
            });
        });
    }
}
