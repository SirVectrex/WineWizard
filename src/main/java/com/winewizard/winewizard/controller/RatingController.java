package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.Rating;
import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.repository.WineRepository;
import com.winewizard.winewizard.service.RatingServiceI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("rating")
public class RatingController {

    private RatingServiceI ratingService;
    private WineRepository wineRepository;

    public RatingController(RatingServiceI ratingService, WineRepository wineRepository) {
        super();
        this.ratingService = ratingService;
        this.wineRepository = wineRepository;
    }

    @GetMapping("/add")
    public String addRating(Model model) {
        Rating rating = new Rating();
        rating.setId((long) -1);
        model.addAttribute("rating", rating);

        return "rating/rate_wine_pick";
    }

    @GetMapping("/addRating/}")
    public String addRating(@RequestParam("winenumber") Long winenumber, Model model) {
        Wine wine = wineRepository.findById(winenumber).orElse(null);
        model.addAttribute("wine", wine);
        return "addRating";
    }



}


