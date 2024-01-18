package com.example.spring_redis.db.user.redis;

import org.springframework.data.repository.CrudRepository;

public interface UserRedisHashRepository extends CrudRepository<UserRedisHash, Long> {
}
