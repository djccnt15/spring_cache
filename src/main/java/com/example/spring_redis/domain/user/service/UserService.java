package com.example.spring_redis.domain.user.service;

import com.example.spring_redis.db.user.jpa.UserEntity;
import com.example.spring_redis.db.user.jpa.UserRepository;
import com.example.spring_redis.domain.user.converter.UserConverter;
import com.example.spring_redis.exception.ApiException;
import com.example.spring_redis.status.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final JedisPool jedisPool;
    
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
}
