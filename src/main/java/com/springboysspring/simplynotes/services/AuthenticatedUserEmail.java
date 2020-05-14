package com.springboysspring.simplynotes.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserEmail {

    public String getAuthenticatedUserEmail() {
        return SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal()
            .toString();
    }

}
