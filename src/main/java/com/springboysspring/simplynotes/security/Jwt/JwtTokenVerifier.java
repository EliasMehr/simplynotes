package com.springboysspring.simplynotes.security.Jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import javax.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private final SecretKey secretKey;
    private final JwtConfiguration jwtConfiguration;

    public JwtTokenVerifier(SecretKey secretKey, JwtConfiguration jwtConfiguration) {
        this.secretKey = secretKey;
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(jwtConfiguration.getAuthorizationHeader());

        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfiguration.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = removeBearerFromToken(authorizationHeader);

            Jws<Claims> claimsJws = decode(token);

            Claims jwsBody = claimsJws.getBody();

            String subject = jwsBody.getSubject(); // The Actual username that we pass to subject variable

            // förstår inte
            List<Map<String, String>> authorities = (List<Map<String, String>>) jwsBody.get("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = getAuthority(authorities);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    subject,
                    null,
                    simpleGrantedAuthorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);


        } catch (JwtException e) {
            e.printStackTrace();
        }

        // After first filter and second filter we want to send back the expected resource back to client
        filterChain.doFilter(request, response);
    }

    @NotNull
    private String removeBearerFromToken(String authorizationHeader) {
        return authorizationHeader.replace(jwtConfiguration.getTokenPrefix(), "");
    }

    @NotNull
    private Set<SimpleGrantedAuthority> getAuthority(List<Map<String, String>> authorities) {
        return authorities.stream()
                .map(auth -> new SimpleGrantedAuthority(auth.get("authority")))
                .collect(Collectors.toSet());
    }

    private Jws<Claims> decode(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }

}
