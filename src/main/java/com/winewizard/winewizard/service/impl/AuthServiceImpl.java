package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.service.AuthServiceI;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthServiceI {
    @Override
    public User getLoggedInUserDetails() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
                return (User) authentication.getPrincipal();
            }

            // Wenn kein Benutzer authentifiziert ist oder die Details nicht verfügbar sind
            return null;
        }
}
