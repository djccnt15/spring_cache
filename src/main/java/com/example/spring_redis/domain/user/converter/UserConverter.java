package com.example.spring_redis.domain.user.converter;

import com.example.spring_redis.annotation.Converter;
import com.example.spring_redis.db.user.jpa.UserEntity;
import com.example.spring_redis.db.user.redis.UserRedisHash;
import com.example.spring_redis.domain.user.controller.model.UserResponse;
import com.example.spring_redis.exception.ApiException;
import com.example.spring_redis.status.StatusCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Converter
public class UserConverter {
    
    public UserResponse toResponse(
        UserEntity entity
    ) {
        return Optional.ofNullable(entity)
            .map(it -> UserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build())
            .orElseThrow(() -> new ApiException(StatusCode.NULL_PONT));
    }
    
    public UserResponse toResponse(
        UserRedisHash userRedisHash
    ) {
        return Optional.ofNullable(userRedisHash)
            .map(it -> UserResponse.builder()
                .id(userRedisHash.getId())
                .name(userRedisHash.getName())
                .email(userRedisHash.getEmail())
                .createdAt(userRedisHash.getCreatedAt())
                .updatedAt(userRedisHash.getUpdatedAt())
                .build())
            .orElseThrow(() -> new ApiException(StatusCode.NULL_PONT));
    }
    
    public HashMap<String, String> toHashMap(
        UserEntity entity
    ) {
        HashMap<String, String> userInfo = new HashMap<>();
        userInfo.put("userId", String.valueOf(entity.getId()));
        userInfo.put("userName", entity.getName());
        userInfo.put("userEmail", entity.getEmail());
        userInfo.put("createdAt", String.valueOf(entity.getCreatedAt()));
        userInfo.put("updatedAt", String.valueOf(entity.getUpdatedAt()));
        
        return userInfo;
    }
    
    public UserEntity hashToEntity(
        Map<String, String> userHash
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        
        return UserEntity.builder()
            .id(Long.parseLong(userHash.get("userId")))
            .name(userHash.get("userName"))
            .email(userHash.get("userEmail"))
            .createdAt(LocalDateTime.parse(userHash.get("createdAt"), formatter))
            .updatedAt(LocalDateTime.parse(userHash.get("updatedAt"), formatter))
            .build();
    }
    
    public UserEntity mapToEntity(
        Map<Object, Object> userHash
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        
        return UserEntity.builder()
            .id(Long.parseLong(String.valueOf(userHash.get("userId"))))
            .name(String.valueOf(userHash.get("userName")))
            .email(String.valueOf(userHash.get("userEmail")))
            .createdAt(LocalDateTime.parse(String.valueOf(userHash.get("createdAt")), formatter))
            .updatedAt(LocalDateTime.parse(String.valueOf(userHash.get("updatedAt")), formatter))
            .build();
    }
}
