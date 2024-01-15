package com.example.spring_redis.domain.user.controller;

import com.example.spring_redis.api.Api;
import com.example.spring_redis.domain.user.business.UserBusiness;
import com.example.spring_redis.domain.user.controller.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserOpenApiController {

    private final UserBusiness userBusiness;
    
    @GetMapping(path = "/{id}")
    public Api<UserResponse> getUser(
        @PathVariable Long id
    ) {
        var response = userBusiness.getUser(id);
        return Api.OK(response);
    }
}