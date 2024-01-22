package com.example.spring_redis.domain.visit.converter;

import com.example.spring_redis.annotation.Converter;
import com.example.spring_redis.db.visit.VisitCountEntity;
import com.example.spring_redis.domain.visit.controller.model.VisitCountResponse;

@Converter
public class VisitCountConverter {
    
    public VisitCountResponse toResponse(VisitCountEntity visitCount) {
        return VisitCountResponse.builder()
            .sessionId(visitCount.getSessionId())
            .visitCount(visitCount.getVisitCount())
            .build();
    }
}
