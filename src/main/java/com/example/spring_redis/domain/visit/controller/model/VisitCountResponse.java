package com.example.spring_redis.domain.visit.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitCountResponse {

    private String sessionId;
    
    private Long visitCount;
}
