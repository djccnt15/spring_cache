package com.example.spring_redis.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum StatusCode implements StatusCodeIfs {

    OK(HttpStatus.OK.value(), 200, "Success"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "Bad Request"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "Internal Server Error"),
    NULL_PONT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512, "Null Point");
    
    private final Integer httpStatusCode;  // http status code
    private final Integer statusCode;  // internal status code
    private final String description;
}
