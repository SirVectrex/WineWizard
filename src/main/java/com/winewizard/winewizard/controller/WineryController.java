package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.config.EmailDetails;
import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.model.Winery;
import com.winewizard.winewizard.service.WineServiceI;
import com.winewizard.winewizard.service.WineryServiceI;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "winery")
public class WineryController {

    private WineryServiceI wineryService;

    public WineryController(WineryServiceI wineService){
        super();
        this.wineryService = wineService;
    }

    @GetMapping ("/{wineryIdentifier}")
    public String getWine(Model model, @PathVariable("wineryIdentifier")String identifier){
        Winery winery = wineryService.getByUrlIdent(identifier);
        if(winery == null) {
            System.out.println("Warning: No winery found with ident: " + identifier);
            return "/home";
        }
        model.addAttribute("emailDetails", new EmailDetails());
        model.addAttribute("winery", winery);

        return "/winery/profile";
    }


}