package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.config.EmailDetails;
import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.Winery;
import com.winewizard.winewizard.repository.WineProjectionI;
import com.winewizard.winewizard.service.AuthServiceI;
import com.winewizard.winewizard.service.WineServiceI;
import com.winewizard.winewizard.service.WineryServiceI;
import com.winewizard.winewizard.service.impl.AuthServiceImpl;
import com.winewizard.winewizard.service.impl.UserServiceImpl;
import com.winewizard.winewizard.service.impl.WineServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "winery")
public class WineryController {

    private final WineryServiceI wineryService;
    private final WineServiceImpl wineServiceI;
    private final AuthServiceI authService;

    public WineryController(WineryServiceI wineryService, WineServiceImpl wineService, AuthServiceImpl authService, UserServiceImpl userService) {
        super();
        this.wineryService = wineryService;
        this.wineServiceI = wineService;
        this.authService = authService;
    }

    @RequestMapping(value = {"profile/{wineryIdentifier}", "profile"})
    public String getWinery(Model model, @PathVariable(value = "wineryIdentifier", required = false) String identifier) {
        if(identifier == null) {
            User userDetail= authService.getLoggedInUserDetails();
            if(userDetail == null ){
                return "customlogin";
            }

            var winery = wineryService.getByWineryByWineryOwnerName(userDetail.getUsername());

            if(winery != null) {
                model.addAttribute("winery", winery);
            }
        } else {
            Winery winery = wineryService.getByUrlIdent(identifier);
            if (winery == null) {
                System.out.println("Warning: No winery found with ident: " + identifier);
                return "home";
            }
            model.addAttribute("winery", winery);
        }
        model.addAttribute("emailDetails", new EmailDetails());

        return "winery/profile";
    }

    @GetMapping("/statistics")
    public String getStatistics(Model model, @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("Error: Auth error");
            return "/error/general";
        }

        var ownerName = authentication.getName();
        var winery = wineryService.getByWineryByWineryOwnerName(ownerName);
        //var allWines = wineServiceI.getAllWinesOfWinery(winery);
        Pageable pageable = PageRequest.of(page, size);

        var allWines = wineServiceI.getWineRatingsByWinery(pageable, winery);
        model.addAttribute("allWines", allWines);
        return "/winery/statistics";
    }
}