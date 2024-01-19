package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.config.MyUserDetails;
import com.winewizard.winewizard.service.EmailServiceI;
import com.winewizard.winewizard.service.UserServiceI;
import com.winewizard.winewizard.service.impl.EmailServiceImpl;
import com.winewizard.winewizard.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class EmailController {

    private final EmailServiceI emailService;
    private final UserServiceI userService;

    @Autowired
    EmailController(EmailServiceI emailService, UserServiceImpl userService){
        this.emailService = emailService;
        this.userService = userService;
    }

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

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public RedirectView verify(@Param("code") String code)
    {
        if (userService.verify(code)) {
            return new RedirectView("registrationSuccessful");
        } else {
            return new RedirectView("registrationFailed");
        }
    }

}