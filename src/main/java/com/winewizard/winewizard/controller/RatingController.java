package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.Rating;
import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.repository.UserRepositoryI;
import com.winewizard.winewizard.repository.WineProjectionI;
import com.winewizard.winewizard.repository.WineRepository;
import com.winewizard.winewizard.service.ApiClient;
import com.winewizard.winewizard.service.RatingServiceI;
import com.winewizard.winewizard.service.WineServiceI;
import com.winewizard.winewizard.service.impl.EmailServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("rating")
public class RatingController {

    private RatingServiceI ratingService;
    private WineRepository wineRepository;

    private UserRepositoryI userRepository;

    private WineServiceI wineService;

    private ApiClient apiClient;

    private EmailServiceImpl emailService;

    public RatingController(RatingServiceI ratingService, WineRepository wineRepository, WineServiceI wineService, UserRepositoryI userRepository, EmailServiceImpl emailService) {
        super();
        this.ratingService = ratingService;
        this.wineRepository = wineRepository;
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
    public String searchWineByName(@RequestParam("name") String name, Model model) {
        Long ean;
        System.out.println("Searching for wine with name: " + name);
        Wine wine = wineRepository.findByNameContainingIgnoreCase(name);

        System.out.println(wine);

        if (wine == null) {
            // No wine found in DB - search API
            System.out.println("No wine found in DB with name: " + name);
            List<Map<String, String>> products = apiClient.searchProducts("Riesling");
            for (Map<String, String> product : products) {
                System.out.println(product);
            }

            model.addAttribute("products", products);
            return  "rating/winelist";
        }
        else {
            // Wine found in DB
            System.out.println("Wine found in DB with name: " + wine.getName() + "and EAN: " + wine.getEan());
            ean = wine.getEan();

            model.addAttribute("wine", wine);
        }

        model.addAttribute("wine", wine);
        return "rating/addRating";
    }

    @PostMapping("/addWine")
    public String addWineToDatabase(@RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @RequestParam("ean") String ean) {
        Long eanLong = Long.parseLong(ean);
        Wine wine = new Wine();
        wine.setEan(eanLong);
        wine.setName(name);
        wine.setDescription(description);
        System.out.println("Attempting to save wine: " + wine);
        wineService.saveWine(wine);
        return "redirect:/rating/addRating?winenumber=" + wine.getId();
    }

    @GetMapping("/addRating")
    public String addRating(@RequestParam("winenumber") Long winenumber, Model model) {
        Wine wine = wineRepository.findById(winenumber).orElse(null);
        model.addAttribute("wine", wine);
        return "rating/addRating";
    }

    @PostMapping("/addRating")
    public String addRating(@RequestParam("winenumber") Long winenumber,
                            @RequestParam("designrating") int designrating,
                            @RequestParam("pricerating") int pricerating,
                            @RequestParam("tasterating") int tasterating) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Attempting to save rating for wine: " + winenumber + "with taste " + tasterating + " design " + designrating + " price " + pricerating);

        Wine wine = wineRepository.findById(winenumber).orElse(null);
        Rating ratingObject = new Rating();
        ratingObject.setRatingDesign(designrating);
        ratingObject.setRatingPrice(pricerating);
        ratingObject.setRatingTaste(tasterating);
        ratingObject.setWine(wine);

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Long userid = userRepository.findByLoginIgnoreCase(username).get().getId();
            ratingObject.setUser(userRepository.findById(userid).orElse(null));
            // return authentication.getName();
        }


        ratingService.saveRating(ratingObject);
        System.out.println("Saved rating: " + ratingObject);
        return "redirect:/";
    }

    @PostMapping("/sendInfo")
    public String sendMail(@RequestParam("email") String email) {
        System.out.println("Sending email to: " + email);
        emailService.sendTopWines(email);
        return "redirect:/allratings";
    }

    @GetMapping("/myratings")
    public String myRatings(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userid = userRepository.findByLoginIgnoreCase(username).get().getId();
        List<Rating> ratings = ratingService.getAllRatingsByUserId(userid);
        model.addAttribute("ratings", ratings);
        return "rating/myratings";
    }




}


