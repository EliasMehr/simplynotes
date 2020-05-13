package com.springboysspring.simplynotes.security.Jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.springboysspring.simplynotes.controllers.response.Response;
import com.springboysspring.simplynotes.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.server.ResponseStatusException;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;
    private final SecretKey jwtSecretKey;
    private final UserRepository userRepository;

    @Autowired
    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager,
        JwtConfiguration jwtConfiguration,
        SecretKey jwtSecretKey,
        UserRepository userRepository) {
        this.authenticationManager = authenticationManager;

        this.jwtConfiguration = jwtConfiguration;
        this.jwtSecretKey = jwtSecretKey;
        this.userRepository = userRepository;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        Authentication authenticate = null;
        try {
            authenticate = getAuthentication(request);
            return authenticationManager.authenticate(authenticate);
        } catch (UnrecognizedPropertyException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("aplication/json");
            response.getWriter().print(e.getMessage());
        }

        return null;
    }

    @NotNull
    private Authentication getAuthentication(HttpServletRequest request) throws IOException {

        JwtUsernameAndPasswordAuthenticationRequest authenticationRequest =
            new ObjectMapper().readValue(request.getInputStream(), JwtUsernameAndPasswordAuthenticationRequest.class);

        return new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword());  // Credentials;

    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult) throws IOException, ServletException {

        String token = Jwts.builder()
            .setSubject(authResult.getName())
            .claim("authorities", authResult.getAuthorities())
            .setIssuedAt(new java.util.Date())
            .setExpiration(Date.valueOf(LocalDate.now().plusWeeks(2)))
            .signWith(jwtSecretKey)
            .compact();

        response.addHeader(jwtConfiguration.getAuthorizationHeader(), jwtConfiguration.getTokenPrefix() + token);
    }


}
