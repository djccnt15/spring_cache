package com.example.spring_redis.exceptionhandler;

import com.example.spring_redis.api.Api;
import com.example.spring_redis.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Object>> apiException(
        ApiException apiException
    ) {
        log.error("", apiException);
        
        var errorCode = apiException.getStatusCodeIfs();
        
        return ResponseEntity
            .status(errorCode.getHttpStatusCode())
            .body(
                Api.ERROR(errorCode, apiException.getStatusDescription())
            );
    }
}
