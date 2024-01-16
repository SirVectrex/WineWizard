package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.config.MyUserDetails;
import com.winewizard.winewizard.service.EmailServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController

public class EmailController {

    @Autowired private EmailServiceI emailService;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public RedirectView
    sendMail()
    {
        MyUserDetails user = getLoggedInUserDetails();
        String recipient = user.getEmail();;

        emailService.sendHtmlMail(recipient);
        return new RedirectView("/");
    }

    public MyUserDetails getLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof MyUserDetails) {
            return (MyUserDetails) authentication.getPrincipal();
        }

        // Wenn kein Benutzer authentifiziert ist oder die Details nicht verfügbar sind
        return null;
    }

}