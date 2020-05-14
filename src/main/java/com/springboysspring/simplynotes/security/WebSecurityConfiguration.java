package com.springboysspring.simplynotes.security;

import com.springboysspring.simplynotes.repositories.UserRepository;
import com.springboysspring.simplynotes.security.Jwt.JwtConfiguration;
import com.springboysspring.simplynotes.security.Jwt.JwtTokenVerifier;
import com.springboysspring.simplynotes.security.Jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.springboysspring.simplynotes.security.auth.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKey;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final MyUserDetailService myUserDetailService;
    private final SecretKey secretKey;
    private final JwtConfiguration jwtConfiguration;
    private final UserRepository userRepository;

    @Autowired
    public WebSecurityConfiguration(MyUserDetailService myUserDetailService, SecretKey secretKey, JwtConfiguration jwtConfiguration,
         UserRepository userRepository) {
        this.myUserDetailService = myUserDetailService;
        this.secretKey = secretKey;
        this.jwtConfiguration = jwtConfiguration;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfiguration, secretKey, userRepository))
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfiguration), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "index", "/user/signup" ,"/css/*", "/js/* ").permitAll()
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .anyRequest()
                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService);
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
