package com.winewizard.winewizard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm() {
        return "general/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String processLogin( @RequestParam("phoneNumber") String phoneNumber,
                                @RequestParam("password") String password,
                                RedirectAttributes redirectAttributes
    ) {
        // Your authentication logic would go here
        // For simplicity, let's assume a hardcoded username and password
        String expectedPhoneNumber = "+1234567890";
        String expectedPassword = "password";

        if (phoneNumber.equals(expectedPhoneNumber) && password.equals(expectedPassword)) {
            // Successful login, you might want to store user information in the session
            // For simplicity, let's just redirect to a success page
            return "redirect:/";
        } else {
            // Failed login, add a flash attribute to show an error message on the login page
            redirectAttributes.addFlashAttribute("error", "Invalid credentials");
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String handleLogout() {
        // Perform any logout logic

        return "redirect:/login";
    }
}
