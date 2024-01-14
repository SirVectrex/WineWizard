package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.WineDTO;
import com.winewizard.winewizard.repository.WineRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.winewizard.winewizard.model.Wine;

import java.util.List;

@Controller
public class GeneralController {

    WineRepository winerepo;

    public GeneralController(WineRepository winerepo){
        super();
        this.winerepo = winerepo;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String home(Model model) {

        winerepo.findWinesWithAverageRatings();
        // System.out.println(winelist);

        String loggedInUsername = getLoggedInUsername();
        model.addAttribute("loggedInUsername", loggedInUsername);

        return "home";
    }

    @RequestMapping(method= RequestMethod.GET, value = "/statistics")
    public String statistics() {
        // folder general/statistics.html
        return "general/statistics";
    }

    @RequestMapping(method= RequestMethod.GET, value = "/add")
    public String addWine() {
        // folder general/statistics.html
        return "wines/add_wine";
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public String feedback() {
        // folder general/feedback.html
        return "general/feedback";
    }

    public String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            return authentication.getName();

        }

        // Wenn kein Benutzer authentifiziert ist
        return "Gast";
    }



}
