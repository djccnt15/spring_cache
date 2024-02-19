package com.example.spring_redis.domain.pubsub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PublishController {
    
    private final RedisTemplate<String, String> redisPubTemplate;
    
    @PostMapping("/events/users/deregister/")
    void publishUserDeregisterEvent() {
        redisPubTemplate.convertAndSend("users:unregister", "500");
    }
}
