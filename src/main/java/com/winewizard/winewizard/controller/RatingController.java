package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.Rating;
import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.model.ZipCode;
import com.winewizard.winewizard.repository.UserRepositoryI;
import com.winewizard.winewizard.repository.WineProjectionI;
import com.winewizard.winewizard.repository.impl.WineRepositoryImpl;
import com.winewizard.winewizard.service.ApiClient;
import com.winewizard.winewizard.service.RatingServiceI;
import com.winewizard.winewizard.service.WineServiceI;
import com.winewizard.winewizard.service.impl.EmailServiceImpl;
import com.winewizard.winewizard.service.impl.WineServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("rating")
public class RatingController {

    private RatingServiceI ratingService;
    private WineRepositoryImpl wineRepository;
    private UserRepositoryI userRepository;
    private WineServiceImpl wineService;
    private ApiClient apiClient;
    private EmailServiceImpl emailService;

    public RatingController(RatingServiceI ratingService, WineRepositoryImpl wineRepositoryI, WineServiceImpl wineService, UserRepositoryI userRepository, EmailServiceImpl emailService) {
        super();
        this.ratingService = ratingService;
        this.wineRepository = wineRepositoryI;
        this.apiClient = new ApiClient();
        this.wineService = wineService;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @GetMapping("/add")
    public String addRating(Model model) {
        Rating rating = new Rating();
        rating.setId((long) -1);
        model.addAttribute("rating", rating);
        return "rating/winePick";
    }

    @PostMapping("/search_by_name")
    public String searchWineByName(@RequestParam("name") String name, Model model){
        System.out.println("DEBUG: Searching for wine with name: " + name);
        Wine wine = wineService.findWine(name);
        if(wine == null){
            System.out.println("DEBUG: Searching with API now...");
            List<Map<String, String>> products = apiClient.searchProducts(name);
            model.addAttribute("products", products);
            return  "rating/winelist";
        }

        System.out.println("DEBUG: Found wine with name: " + wine.getName());
        Rating rating = new Rating();
        rating.setWine(wine);
        model.addAttribute("rating",rating);
        return "rating/addRating";

    }

    @PostMapping("/addWine")
    public String addWineToDatabase(@RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @RequestParam("ean") String ean) {

        try {
            Wine wine = wineService.saveWine(Long.parseLong(ean), name, description);
            return "redirect:/rating/addRating?winenumber=" + wine.getId();
        } catch (Exception e){
            // possibly do error handling
        }
        return "rating/addRating";

    }

    @GetMapping("/addRating")
    public String addRating(@RequestParam("winenumber") Long winenumber, Model model) {
        Wine wine = wineRepository.findById(winenumber).orElse(null);
        Rating rating = new Rating();
        rating.setWine(wine);
        model.addAttribute("rating", rating);
        return "rating/addRating";
    }

    @PostMapping("/addRating")
    public String addRating(@ModelAttribute Rating rating){

        // this method requires the wine to be part of the input already
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByLoginIgnoreCase(authentication.getName()).get();

        rating.setUser(user);
        ratingService.saveRating(rating);

        return "redirect:/rating/myratings";
    }


    @PostMapping("/sendInfo")
    public String sendMail(@RequestParam("email") String email) {
        System.out.println("Sending email to: " + email);
        emailService.sendTopWines(email);
        return "redirect:/allratings";
    }

    @GetMapping("/myratings")
    public String myRatings2(Model model,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "5") int size) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userid = userRepository.findByLoginIgnoreCase(authentication.getName()).get().getId();
        Pageable pageable = PageRequest.of(page, size);
        Page<Rating> ratingPage =  ratingService.getAllRatingsByUserID(pageable, userid);

        model.addAttribute("ratings", ratingPage);
        var prevPage = page -1;
        var nextPage = page +1;
        model.addAttribute("hrefLinkPrev", "/rating/myratings?page="+ prevPage +"&size=" + size);
        model.addAttribute("hrefLinkNext", "/rating/myratings?page="+ nextPage +"&size=" + size);
        model.addAttribute("hrefLinkPagePre", "/rating/myratings?page=");
        model.addAttribute("hrefLinkPagePost", "&size=" + size);
        model.addAttribute("allowEditing", true);


        return "rating/myratings";
    }

    @GetMapping("/delete")
    public String deleteRating(@RequestParam("id") Long id) {
        System.out.println("LOG: Deleted rating with id: " + id);
        ratingService.deleteRatingById(id);
        return "redirect:/rating/myratings"; // Redirect to the page showing ratings
    }



    @GetMapping("/myarea")
    public String myArea(Model model,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "5") int size) {

        String loggedInUsername = getLoggedInUsername();
        Pageable pageable = PageRequest.of(page, size);
        ZipCode zipCode;
        int zipCodeInt = 0;
        if (!loggedInUsername.equals("anonymousUser")) {
            zipCode = userRepository.findByLoginIgnoreCase(loggedInUsername).get().getZipCode();
            zipCodeInt = Integer.parseInt((zipCode.getZipCode()));
        }

        Page<WineProjectionI> winePage =  wineRepository.getWinesByZipCodewParamwPage(zipCodeInt, pageable);
        model.addAttribute("wines", winePage);

        return "rating/localratings";
    }


    public String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        // fallback
        return "Guest";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Rating rating = ratingService.findRatingById(id);

        model.addAttribute("rating", rating);
        return "rating/updateRating";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            // open for further debugging
            return "redirect:/rating/myratings";
        }

        ratingService.saveRating(rating);
        return "redirect:/rating/myratings";
    }

}


