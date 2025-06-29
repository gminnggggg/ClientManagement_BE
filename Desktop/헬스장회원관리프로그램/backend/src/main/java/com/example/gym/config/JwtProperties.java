package com.example.gym.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt") // 'jwt' 접두사를 가진 프로퍼티를 바인딩
public class JwtProperties {
    private String secret;
    private long expiration;
}