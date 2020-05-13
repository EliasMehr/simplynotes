package com.springboysspring.simplynotes.security.Jwt;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;
import javax.crypto.SecretKey;
import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@AllArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;
    private final SecretKey jwtSecretKey;
    private final UserRepository userRepository;


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
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain, Authentication authResult) {
        String token = generateJwtToken(authResult);
        addUserObjectInResponse(response, authResult, token);

    }

    @SneakyThrows
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
        org.springframework.security.core.AuthenticationException failed) {
        response.sendError(BAD_REQUEST.value());
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
    private void addUserObjectInResponse(HttpServletResponse response, Authentication authResult, String token) {
        Optional<User> byEmail = userRepository.findByEmail(authResult.getName());
        sendResponseBody(response, byEmail.get(), OK, token);
    }

    @SneakyThrows
    private void sendResponseBody(HttpServletResponse response, User responseBody, HttpStatus status, String token) {
        var data = new HashMap<>();
        data.put("status", status.value());
        data.put("body", responseBody);
        data.put(jwtConfiguration.getAuthorizationHeader(), jwtConfiguration.getTokenPrefix() + token);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), data);
        response.getOutputStream().flush();
    }


}
