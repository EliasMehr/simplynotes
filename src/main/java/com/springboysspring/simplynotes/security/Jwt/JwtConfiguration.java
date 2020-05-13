package com.springboysspring.simplynotes.security.Jwt;

import com.google.common.net.HttpHeaders;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfiguration {

    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpirationAfterDays;
    private String getWrongCredentialsMessage;

    public JwtConfiguration() {
    }

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }

    public String getSecretKey() {
        return secretKey;
    }


}
