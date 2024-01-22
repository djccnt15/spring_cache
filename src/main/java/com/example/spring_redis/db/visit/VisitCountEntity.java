package com.example.spring_redis.db.visit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class VisitCountEntity {
    
    private String sessionId;
    
    private Long visitCount;
}
