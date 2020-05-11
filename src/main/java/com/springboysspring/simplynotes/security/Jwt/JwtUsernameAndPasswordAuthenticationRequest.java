package com.springboysspring.simplynotes.security.Jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtUsernameAndPasswordAuthenticationRequest {

    private String username;
    private String password;

    public JwtUsernameAndPasswordAuthenticationRequest() {
    }
}
