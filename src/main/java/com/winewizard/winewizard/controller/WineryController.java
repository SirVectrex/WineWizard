package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.config.EmailDetails;
import com.winewizard.winewizard.config.MyUserDetails;
import com.winewizard.winewizard.model.Winery;
import com.winewizard.winewizard.service.AuthServiceI;
import com.winewizard.winewizard.service.WineServiceI;
import com.winewizard.winewizard.service.WineryServiceI;
import com.winewizard.winewizard.service.impl.AuthServiceImpl;
import com.winewizard.winewizard.service.impl.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "winery")
public class WineryController {

    private final WineryServiceI wineryService;
    private final WineServiceI wineServiceI;
    private final AuthServiceI authService;

    public WineryController(WineryServiceI wineryService, WineServiceI wineService, AuthServiceImpl authService, UserServiceImpl userService) {
        super();
        this.wineryService = wineryService;
        this.wineServiceI = wineService;
        this.authService = authService;
    }

    @RequestMapping(value = {"profile/{wineryIdentifier}", "profile"})
    public String getWinery(Model model, @PathVariable(value = "wineryIdentifier", required = false) String identifier) {
        if(identifier == null) {
            MyUserDetails userDetail= authService.getLoggedInUserDetails();
            if(userDetail == null ){
                return "/customlogin";
            }

            var winery = wineryService.getByWineryByWineryOwnerName(userDetail.getUsername());

            if(winery != null) {
                model.addAttribute("winery", winery);
            }
        } else {
            Winery winery = wineryService.getByUrlIdent(identifier);
            if (winery == null) {
                System.out.println("Warning: No winery found with ident: " + identifier);
                return "/home";
            }
            model.addAttribute("winery", winery);
        }
        model.addAttribute("emailDetails", new EmailDetails());

        return "winery/profile";
    }

    @GetMapping("/statistics")
    public String getStatistics(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            var ownerName = authentication.getName();
            var winery = wineryService.getByWineryByWineryOwnerName(ownerName);
            var allWines = wineServiceI.getAllWinesOfWinery(winery);
            //TODO: display the wines in frontend + Klassen umbenennen
            model.addAttribute("allWines", allWines);
            return "/winery/statistics";
        }
        System.out.println("Error: Auth error");
        return "/error/general";
    }

}