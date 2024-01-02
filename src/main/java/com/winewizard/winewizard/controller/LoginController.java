package com.winewizard.winewizard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/customlogin")
    public String login() {
        return "/customlogin"; // This will return the login.html page
    }
}