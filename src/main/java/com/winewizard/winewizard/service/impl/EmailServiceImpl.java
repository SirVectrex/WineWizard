package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.winewizard.winewizard.service.HtmlFileReaderService;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    private final HtmlFileReaderService htmlFileReaderService;

    @Value("${spring.mail.username}")
    private String sender;

    public EmailServiceImpl(HtmlFileReaderService htmlFileReaderService) {
        this.htmlFileReaderService = htmlFileReaderService;
    }

    public String sendHtmlMail(String recipient) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(sender);
            helper.setTo(recipient);
            helper.setSubject("Newsletter");

            // HTML content for the email body
            String htmlBody = htmlFileReaderService.readHtmlFile("classpath:templates/general/newsletter.html");

            // Set the HTML content to true
            helper.setText(htmlBody, true);

            // Send the email
            javaMailSender.send(mimeMessage);

            return "Newsletter Sent Successfully...";
        } catch (MessagingException e) {
            return "Error while Sending Mail";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}