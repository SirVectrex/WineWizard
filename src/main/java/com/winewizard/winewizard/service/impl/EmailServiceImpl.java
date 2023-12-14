package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.config.EmailDetails;
import com.winewizard.winewizard.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
// Class
// Implementing EmailService interface
public class EmailServiceImpl implements EmailService {

    @Autowired private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;


    public String sendSimpleMail(String recipient)
    {

        // Try block to check for exceptions
        try {

            // Getting the riddle text from the external API
            //String riddle = emailService.getRiddle();

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(recipient);
            /// TODO: 14.12.2023 Newsletter mail body 
            mailMessage.setText("Newsletter Riddle: ");
            mailMessage.setSubject("Newsletter");

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Newsletter Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
}
