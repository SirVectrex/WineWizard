package com.winewizard.winewizard.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LanguageController {

    @GetMapping("/changeLanguage")
    public String changeLanguage(@RequestParam("lang") String lang, HttpServletRequest request) {
        // methods sets correct language and redirects to the home screen
        request.getSession().setAttribute("lang", lang);
        return "redirect:/";
    }
}
