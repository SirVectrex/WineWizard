package com.winewizard.winewizard.service;

// Interface
public interface EmailServiceI {

    // Method
    // To send a simple email
    Void sendHtmlMail(String recipient);

    Void sendTopWines(String recipient);
}