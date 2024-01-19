package com.winewizard.winewizard.service;

import com.winewizard.winewizard.config.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public interface AuthServiceI {

    public MyUserDetails getLoggedInUserDetails();
}
