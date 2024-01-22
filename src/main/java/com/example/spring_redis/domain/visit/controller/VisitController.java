package com.example.spring_redis.domain.visit.controller;

import com.example.spring_redis.api.Api;
import com.example.spring_redis.domain.visit.controller.model.VisitCountResponse;
import com.example.spring_redis.domain.visit.business.VisitCountBusiness;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/visit")
@RequiredArgsConstructor
public class VisitController {
    
    private final VisitCountBusiness visitCountBusiness;

    @GetMapping(path = "/count")
    public Api<VisitCountResponse> visitCount(HttpSession session) {
        var response = visitCountBusiness.increaseCount(session);
        return Api.OK(response);
    }
}
