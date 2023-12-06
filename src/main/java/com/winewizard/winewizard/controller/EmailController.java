package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.config.EmailDetails;
import com.winewizard.winewizard.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class EmailController {

    @Autowired private EmailService emailService;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public String
    sendMail(@ModelAttribute EmailDetails details)
    {

        return emailService.sendSimpleMail(details);
    }

}