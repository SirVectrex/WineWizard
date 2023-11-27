package com.winewizard.winewizard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WineController {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String home() {
        return "home";
    }

}
