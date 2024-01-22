package com.example.spring_redis.domain.visit.business;

import com.example.spring_redis.annotation.Business;
import com.example.spring_redis.domain.visit.controller.model.VisitCountResponse;
import com.example.spring_redis.domain.visit.converter.VisitCountConverter;
import com.example.spring_redis.domain.visit.service.VisitCountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class VisitCountBusiness {
    
    private final VisitCountService visitCountService;
    private final VisitCountConverter visitCountConverter;

    public VisitCountResponse increaseCount(HttpSession session) {
        var visitCount = visitCountService.increaseCount(session);
        var response = visitCountConverter.toResponse(visitCount);
        return response;
    }
}
