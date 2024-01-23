package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.Rating;
import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.repository.RatingRepositoryI;
import com.winewizard.winewizard.repository.StatsProjectionI;
import com.winewizard.winewizard.repository.UserRepositoryI;
import com.winewizard.winewizard.repository.WineProjectionI;
import com.winewizard.winewizard.repository.impl.WineRepositoryImpl;
import com.winewizard.winewizard.service.AuthServiceI;
import com.winewizard.winewizard.service.RatingServiceI;
import com.winewizard.winewizard.service.impl.AuthServiceImpl;
import com.winewizard.winewizard.service.impl.RatingServiceImpl;
import com.winewizard.winewizard.service.impl.WineServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GeneralController {

    WineRepositoryImpl winerepo;

    RatingRepositoryI ratingRepositoryI;

    WineServiceImpl wineServiceImpl;
    RatingServiceI ratingService;

    UserRepositoryI userRepository;
    AuthServiceI authService;

    public GeneralController(WineRepositoryImpl winerepo, RatingRepositoryI ratingRepositoryI,
                             WineServiceImpl wineServiceImpl, UserRepositoryI userRepository,
                             AuthServiceImpl authService, RatingServiceImpl ratingService){
        super();
        this.winerepo = winerepo;
        this.ratingRepositoryI = ratingRepositoryI;
        this.wineServiceImpl = wineServiceImpl;
        this.userRepository = userRepository;
        this.authService = authService;
        this.ratingService = ratingService;
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
        // get users zip code from auth
        String loggedInUsername = getLoggedInUsername();
        List<StatsProjectionI> stats =  winerepo.getStats();

        model.addAttribute("loggedInUsername", loggedInUsername);
        model.addAttribute("stat", stats.get(0));

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
        return "addWine";
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

    @GetMapping("/notVerified")
    public String success() {
        return "general/registration/notVerified";
    }

    @GetMapping(value = {"/profile", "/profile/{profileId}"})
    public String profile(Model model, @PathVariable(required = false) String profileId,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "5") int size) {
        User user;
        if(profileId == null) {
            user = authService.getLoggedInUserDetails();
        } else{
           user = userRepository.findUserByPersonalProfileId(profileId);
           if(user == null) {
               return "error/404";
           } else{
               model.addAttribute("user", user);
           }
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Rating> ratingPage =  ratingService.getAllRatingsByUserID(pageable, user.getId());

        model.addAttribute("ratings", ratingPage);
        model.addAttribute("user", user);
        var prevPage = page -1;
        var nextPage = page +1;
        var shareLink = user.getShareLink();
        model.addAttribute("hrefLinkPrev", shareLink +"?page="+ prevPage +"&size=" + size);
        model.addAttribute("hrefLinkNext", shareLink +"?page="+ nextPage +"&size=" + size);
        model.addAttribute("hrefLinkPagePre", shareLink +"?page=");
        model.addAttribute("hrefLinkPagePost", "&size=" + size);
        model.addAttribute("allowEditing", false);

        return "general/profile";
    }
}
