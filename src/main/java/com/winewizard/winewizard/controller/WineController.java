package com.winewizard.winewizard.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.repository.WineRepository;
import com.winewizard.winewizard.service.WineServiceI;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "wines")
public class WineController {

    private WineServiceI wineService;

    public WineController(WineServiceI wineService){
        super();
        this.wineService = wineService;
    }

    @GetMapping ("/add")
    public String addWine(Model model){
        Wine wine = new Wine();
        wine.setId((long) -1);
        model.addAttribute("wine", wine);
        wineService.saveWine(wine);

        return "/wines/add_wine";
    }

    @PostMapping("/add")
    public String addWine(@Valid Wine wine, BindingResult result, Model model){
        if(result.hasErrors()){
            System.out.println(result.getAllErrors().toString());
            return "/home";
        }

        wineService.saveWine(wine);

        return "/home";
    }

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