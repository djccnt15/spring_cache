package com.example.spring_redis.exception;

import com.example.spring_redis.status.StatusCodeIfs;

public interface ApiExceptionIfs {
    
    StatusCodeIfs getStatusCodeIfs();
    String getStatusDescription();
}
