package com.springboysspring.simplynotes.security.Jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtUsernameAndPasswordAuthenticationRequest {

    private String email;
    private String password;

}
