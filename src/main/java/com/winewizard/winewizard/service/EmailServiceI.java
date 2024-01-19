package com.winewizard.winewizard.service;

import com.winewizard.winewizard.model.User;

// Interface
public interface EmailServiceI {

    // Method
    // To send a simple email
    Void sendHtmlMail(String recipient);

    Void sendTopWines(String recipient);

    Void sendVerificaiton(User recipient);

}