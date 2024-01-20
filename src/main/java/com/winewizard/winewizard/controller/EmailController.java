package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.service.AuthServiceI;
import com.winewizard.winewizard.service.EmailServiceI;
import com.winewizard.winewizard.service.UserServiceI;
import com.winewizard.winewizard.service.impl.AuthServiceImpl;
import com.winewizard.winewizard.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class EmailController {

    private final EmailServiceI emailService;
    private final UserServiceI userService;

    private  final AuthServiceI authService;

    @Autowired
    EmailController(EmailServiceI emailService, UserServiceImpl userService, AuthServiceImpl authService){
        this.emailService = emailService;
        this.userService = userService;
        this.authService = authService;
    }

    // Sending a simple Email
    @PostMapping("/sendMail")
    public RedirectView
    sendMail()
    {
        User user= authService.getLoggedInUserDetails();
        String recipient = user.getEmail();;

        emailService.sendHtmlMail(recipient);
        return new RedirectView("/");
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