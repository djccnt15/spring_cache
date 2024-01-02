package com.example.spring_redis.api;

import com.example.spring_redis.status.StatusCode;
import com.example.spring_redis.status.StatusCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {
    
    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;
    
    public static Result OK() {
        return Result.builder()
            .resultCode(StatusCode.OK.getStatusCode())
            .resultMessage(StatusCode.OK.getDescription())
            .resultDescription("OK")
            .build();
    }
    
    public static Result ERROR(StatusCodeIfs statusCodeIfs) {
        return Result.builder()
            .resultCode(statusCodeIfs.getStatusCode())
            .resultMessage(statusCodeIfs.getDescription())
            .resultDescription("ERROR")
            .build();
    }
    
    public static Result ERROR(StatusCodeIfs statusCodeIfs, Throwable tx) {
        return Result.builder()
            .resultCode(statusCodeIfs.getStatusCode())
            .resultMessage(statusCodeIfs.getDescription())  // not recommended to return error message to client
            .resultDescription(tx.getLocalizedMessage())
            .build();
    }
    
    public static Result ERROR(StatusCodeIfs statusCodeIfs, String description) {
        return Result.builder()
            .resultCode(statusCodeIfs.getStatusCode())
            .resultMessage(statusCodeIfs.getDescription())
            .resultDescription(description)
            .build();
    }
}
