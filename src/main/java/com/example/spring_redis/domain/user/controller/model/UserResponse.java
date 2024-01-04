package com.example.spring_redis.domain.user.controller.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    
    private String name;
    
    private String email;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}
