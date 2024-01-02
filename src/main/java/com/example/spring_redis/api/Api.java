package com.example.spring_redis.api;

import com.example.spring_redis.status.StatusCodeIfs;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {
    
    private Result result;
    
    @Valid
    private T body;
    
    public static <T> Api<T> OK(T data) {
        var api = new Api<T>();
        api.result = Result.OK();
        api.body = data;
        return api;
    }
    
    public static Api<Object> ERROR(Result result) {
        var api = new Api<Object>();
        api.result = result;
        return api;
    }
    
    public static Api<Object> ERROR(StatusCodeIfs statusCodeIfs) {
        var api = new Api<Object>();
        api.result = Result.ERROR(statusCodeIfs);
        return api;
    }
    
    public static Api<Object> ERROR(StatusCodeIfs statusCodeIfs, Throwable tx) {
        var api = new Api<Object>();
        api.result = Result.ERROR(statusCodeIfs, tx);
        return api;
    }
    
    public static Api<Object> ERROR(StatusCodeIfs statusCodeIfs, String description) {
        var api = new Api<Object>();
        api.result = Result.ERROR(statusCodeIfs, description);
        return api;
    }
}
