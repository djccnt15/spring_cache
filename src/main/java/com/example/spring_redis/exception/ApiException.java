package com.example.spring_redis.exception;

import com.example.spring_redis.status.StatusCodeIfs;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException implements ApiExceptionIfs {
    
    private final StatusCodeIfs statusCodeIfs;
    private final String statusDescription;
    
    public ApiException(StatusCodeIfs statusCodeIfs) {
        super(statusCodeIfs.getDescription());
        this.statusCodeIfs = statusCodeIfs;
        this.statusDescription = statusCodeIfs.getDescription();
    }
    
    public ApiException(StatusCodeIfs statusCodeIfs, String statusDescription) {
        super(statusDescription);
        this.statusCodeIfs = statusCodeIfs;
        this.statusDescription = statusDescription;
    }
    
    public ApiException(StatusCodeIfs statusCodeIfs, Throwable tx) {
        super(tx);
        this.statusCodeIfs = statusCodeIfs;
        this.statusDescription = statusCodeIfs.getDescription();
    }
    
    public ApiException(StatusCodeIfs statusCodeIfs, Throwable tx, String statusDescription) {
        super(tx);
        this.statusCodeIfs = statusCodeIfs;
        this.statusDescription = statusDescription;
    }
}
