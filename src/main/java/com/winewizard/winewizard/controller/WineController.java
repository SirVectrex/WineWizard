package com.winewizard.winewizard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WineController {

    @RequestMapping(method = RequestMethod.GET, value = "/addRating")
    public String addRating() {
        return "wines/rate_wine";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/showAll")
    public String showAll() {
        return "wines/all_wines";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/add_Description")
    public String add_Description() {
        return "wines/add_Description";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/sendAll")
    public String sendAll() {
        return "wines/send_all_wines";
    }

}