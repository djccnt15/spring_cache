package com.example.spring_redis.domain.user.business;

import com.example.spring_redis.annotation.Business;
import com.example.spring_redis.domain.user.controller.model.UserResponse;
import com.example.spring_redis.domain.user.converter.UserConverter;
import com.example.spring_redis.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Business
public class UserBusiness {
    
    private final UserService userService;
    private final UserConverter userConverter;
    
    public UserResponse getUser(Long id) {
        var userEntity = userService.getUser(id);
        var response = userConverter.toResponse(userEntity);
        return response;
    }
    
    public UserResponse getUserWithTemplate(Long id) {
        var userEntity = userService.getUserWithTemplate(id);
        var response = userConverter.toResponse(userEntity);
        return response;
    }
    
    public UserResponse getUserWithGeneric(Long id) {
        var userEntity = userService.getUserWithGeneric(id);
        var response = userConverter.toResponse(userEntity);
        return response;
    }
}
