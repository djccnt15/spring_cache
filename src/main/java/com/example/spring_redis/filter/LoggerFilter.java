package com.example.spring_redis.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
public class LoggerFilter implements Filter {
    
    @Override
    public void doFilter(
        ServletRequest request, ServletResponse response, FilterChain chain
    ) throws IOException, ServletException {
        
        // request/response consume 후에 또 읽을 수 있도록 cashing 해주는 wrapper 필요
        var req = new ContentCachingRequestWrapper((HttpServletRequest) request);
        var res = new ContentCachingResponseWrapper((HttpServletResponse) response);
        
        log.info("INIT URI: {}", ((HttpServletRequest) request).getRequestURI());
        
        // filter 실행 전
        
        chain.doFilter(req, res);
        
        // filter 실행 후
        
        // request info
        var headerNames = req.getHeaderNames();
        var headerValues = new StringBuilder();
        
        headerNames.asIterator().forEachRemaining(headerKey -> {
            var headerValue = req.getHeader(headerKey);
            headerValues
                .append("[")
                .append(headerKey)
                .append(" : ")
                .append(headerValue)
                .append("] ");
        });
        
        var requestBody = new String(req.getContentAsByteArray());
        var uri = req.getRequestURI();
        var method = req.getMethod();
        
        log.info(">>> uri: {}, method: {}, header: {}, body: {}", uri, method, headerValues, requestBody);
        
        // response info
        var responseHeaderValues = new StringBuilder();
        
        res.getHeaderNames().forEach(headerKey -> {
            var headerValue = res.getHeader(headerKey);
            responseHeaderValues
                .append("[")
                .append(headerKey)
                .append(" : ")
                .append(headerValue)
                .append("] ");
        });
        
        var responseBody = new String(res.getContentAsByteArray());
        
        log.info("<<< uri: {}, method: {}, header: {}, body: {}", uri, method, responseHeaderValues, responseBody);
        
        res.copyBodyToResponse();  // response Body consume 되었기 때문에 반드시 필요
    }
}
