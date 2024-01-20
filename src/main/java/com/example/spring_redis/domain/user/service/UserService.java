package com.example.spring_redis.domain.user.service;

import com.example.spring_redis.db.user.jpa.UserEntity;
import com.example.spring_redis.db.user.jpa.UserRepository;
import com.example.spring_redis.db.user.redis.UserRedisHash;
import com.example.spring_redis.db.user.redis.UserRedisHashRepository;
import com.example.spring_redis.domain.user.converter.UserConverter;
import com.example.spring_redis.exception.ApiException;
import com.example.spring_redis.status.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.Duration;
import java.util.Map;

import static com.example.spring_redis.config.CacheConfig.CACHE1;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final UserRedisHashRepository userRedisHashRepository;
    private final UserConverter userConverter;
    private final JedisPool jedisPool;
    private final RedisTemplate<String, UserEntity> userRedisTemplate;
    private final RedisTemplate<String, Object> objectRedisTemplate;
    
    public UserEntity getUser(final Long id) {
        try (Jedis jedis = jedisPool.getResource()) {
            var userRedis = "users:%d".formatted(id);
            
            Map<String, String> userInfo = jedis.hgetAll(userRedis);
            
            if (userInfo.isEmpty()) {
                var userEntity = userRepository.findById(id);
                var entity = userEntity.orElseThrow(() -> new ApiException(StatusCode.NULL_PONT));
                
                var userHash = userConverter.toHashMap(entity);
                jedis.hset(userRedis, userHash);
                jedis.expire(userRedis, 30);
                
                return entity;
            }
            
            return userConverter.hashToEntity(userInfo);
        }
    }
    
    public UserEntity getUserWithTemplate(final Long id) {
        var userRedis = "usersWithTemplate:%d".formatted(id);
        
        var cachedUser = userRedisTemplate.opsForHash().entries(userRedis);
        
        if (cachedUser.isEmpty()) {
            var userEntity = userRepository.findById(id);
            var user = userEntity.orElseThrow(() -> new ApiException(StatusCode.NULL_PONT));
            
            var userHash = userConverter.toHashMap(user);
            userRedisTemplate.opsForHash().putAll(userRedis, userHash);
            userRedisTemplate.expire(userRedis, Duration.ofSeconds(30));
            
            return user;
        }
        
        return userConverter.mapToEntity(cachedUser);
    }
    
    public UserEntity getUserWithGeneric(final Long id) {
        var userRedis = "usersWithGeneric:%d".formatted(id);
        
        var cachedUser = objectRedisTemplate.opsForHash().entries(userRedis);
        
        if (cachedUser.isEmpty()) {
            var userEntity = userRepository.findById(id);
            var user = userEntity.orElseThrow(() -> new ApiException(StatusCode.NULL_PONT));
            
            var userHash = userConverter.toHashMap(user);
            objectRedisTemplate.opsForHash().putAll(userRedis, userHash);
            objectRedisTemplate.expire(userRedis, Duration.ofSeconds(30));
            
            return user;
        }
        
        return userConverter.mapToEntity(cachedUser);
    }
    
    public UserRedisHash getUserWithRedisHash(final Long id) {
        var cachedUser = userRedisHashRepository.findById(id).orElseGet(() -> {
            UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new ApiException(StatusCode.NULL_PONT));
            
            return userRedisHashRepository.save(
                UserRedisHash.builder()
                    .id(userEntity.getId())
                    .name(userEntity.getName())
                    .email(userEntity.getEmail())
                    .createdAt(userEntity.getCreatedAt())
                    .updatedAt(userEntity.getUpdatedAt())
                    .build());
        });
        
        return cachedUser;
    }
    
    @Cacheable(cacheNames = CACHE1, key = "'user:' + #id")
    public UserEntity getUserWithCache(final Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
