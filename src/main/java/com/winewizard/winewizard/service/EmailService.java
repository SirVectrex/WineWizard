package com.winewizard.winewizard.service;

import com.winewizard.winewizard.config.EmailDetails;

// Interface
public interface EmailService {

    // Method
    // To send a simple email
    Void sendHtmlMail(String recipient);
}