package com.example.spring_redis;

import com.example.spring_redis.db.user.UserEntity;
import com.example.spring_redis.db.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringRedisApplication implements ApplicationRunner {
    
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisApplication.class, args);
    }
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.save(UserEntity.builder().name("a").email("aa@test.com").build());
        userRepository.save(UserEntity.builder().name("b").email("bb@test.com").build());
        userRepository.save(UserEntity.builder().name("c").email("cc@test.com").build());
        userRepository.save(UserEntity.builder().name("d").email("dd@test.com").build());
    }
}
