package com.example.spring_redis.domain.visit.service;

import com.example.spring_redis.db.visit.VisitCountEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class VisitCountService {
    
    public VisitCountEntity increaseCount(HttpSession session) {
        Integer visitCount = (Integer) session.getAttribute("visit-count");
        if (visitCount == null) {
            visitCount = 0;
        }
        session.setAttribute("visit-count", ++visitCount);
        
        return VisitCountEntity.builder()
            .sessionId(session.getId())
            .visitCount(Long.valueOf(visitCount))
            .build();
    }
}
