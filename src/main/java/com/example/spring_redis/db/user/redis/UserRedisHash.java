package com.example.spring_redis.db.user.redis;

import com.example.spring_redis.db.BaseRedisHash;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@RedisHash(value = "redisHash-user", timeToLive = 30L)
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class UserRedisHash extends BaseRedisHash {
    
    @Column(length = 30)
    private String name;
    
    @Indexed
    private String email;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}
