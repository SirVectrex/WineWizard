package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.WineDTO;
import com.winewizard.winewizard.repository.RatingRepositoryI;
import com.winewizard.winewizard.repository.WineProjectionI;
import com.winewizard.winewizard.repository.WineRepository;
import com.winewizard.winewizard.service.impl.WineServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GeneralController {

    WineRepository winerepo;

    RatingRepositoryI ratingRepositoryI;

    WineServiceImpl wineServiceImpl;

    public GeneralController(WineRepository winerepo, RatingRepositoryI ratingRepositoryI, WineServiceImpl wineServiceImpl){
        super();
        this.winerepo = winerepo;
        this.ratingRepositoryI = ratingRepositoryI;
        this.wineServiceImpl = wineServiceImpl;

    }

    @RequestMapping(method = RequestMethod.GET, value = "/allratings")
    public String listWines(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<WineProjectionI> winePage = wineServiceImpl.getWineRatings(pageable);
        model.addAttribute("wines", winePage);
        return "general/allratings";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String home(Model model) {

        List<WineProjectionI> winelist = wineServiceImpl.getWineRatings();
                // winerepo.findWinesWithAverageRa
        String loggedInUsername = getLoggedInUsername();
        model.addAttribute("loggedInUsername", loggedInUsername);

        model.addAttribute("wines", winelist);

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
