package com.springboysspring.simplynotes.security.Jwt;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import javax.crypto.SecretKey;
import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;
    private final SecretKey jwtSecretKey;
    private final UserRepository userRepository;


    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager,
        JwtConfiguration jwtConfiguration, SecretKey jwtSecretKey, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtConfiguration = jwtConfiguration;
        this.jwtSecretKey = jwtSecretKey;
        this.userRepository = userRepository;
    }


    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        try {
            JwtUsernameAndPasswordAuthenticationRequest authenticationRequest =
                new ObjectMapper().readValue(request.getInputStream(), JwtUsernameAndPasswordAuthenticationRequest.class);

            Authentication authenticate = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), // Principal
                authenticationRequest.getPassword()  // Credentials
            );
            return authenticationManager.authenticate(authenticate);
        } catch (AuthenticationException | MismatchedInputException | JsonParseException e) {
            // One of the AuthenticationException must be thrown to invoke unsuccessfulAuthentication()
            //@SneakThrows doesn't handle that kind of Exception
            throw new AuthenticationCredentialsNotFoundException(e.getMessage());
        }
    }

    @SneakyThrows
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult) {
        String token = generateJwtToken(authResult);
        sendUserObjectInResponse(response, authResult, token);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
        org.springframework.security.core.AuthenticationException failed) {
        sendResponseBody(response, jwtConfiguration.getGetWrongCredentialsMessage(), BAD_REQUEST);
    }

    private String generateJwtToken(Authentication authResult) {
        return Jwts.builder()
            .setSubject(authResult.getName())
            .claim("authorities", authResult.getAuthorities())
            .setIssuedAt(new java.util.Date())
            .setExpiration(Date.valueOf(LocalDate.now().plusDays(jwtConfiguration.getTokenExpirationAfterDays())))
            .signWith(jwtSecretKey)
            .compact();
    }

    @SneakyThrows
    private void sendUserObjectInResponse(HttpServletResponse response, Authentication authResult, String token) {
        Optional<User> byEmail = userRepository.findByEmail(authResult.getName());
        String jsonPayload = getObjectAsJson(byEmail);
        response.addHeader(jwtConfiguration.getAuthorizationHeader(), jwtConfiguration.getTokenPrefix() + token);
        sendResponseBody(response, jsonPayload, OK);

    }

    @SneakyThrows
    private void sendResponseBody(HttpServletResponse response, String responseBody, HttpStatus status) {
        response.setStatus(status.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().print(responseBody);
        response.getWriter().flush();
    }

    private String getObjectAsJson(Optional<User> byEmail) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(byEmail.get());
    }


}
