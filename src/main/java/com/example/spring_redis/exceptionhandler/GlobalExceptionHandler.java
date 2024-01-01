package com.example.spring_redis.exceptionhandler;

import com.example.spring_redis.api.Api;
import com.example.spring_redis.status.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MAX_VALUE)  // max value is explicit of default
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Api<Object>> exception(
        Exception exception
    ) {
        log.error("", exception);
        
        return ResponseEntity
            .status(500)
            .body(
                Api.ERROR(StatusCode.SERVER_ERROR)
            );
    }
}
