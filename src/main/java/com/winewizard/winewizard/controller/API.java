package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.Rating;
import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.repository.UserRepositoryI;
import com.winewizard.winewizard.repository.WineProjectionI;
import com.winewizard.winewizard.repository.impl.WineRepositoryImpl;
import com.winewizard.winewizard.service.RatingServiceI;
import com.winewizard.winewizard.service.impl.RatingServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class API {

    WineRepositoryImpl wineRepositoryI;
    UserRepositoryI userRepository;

    RatingServiceImpl ratingService;

    public API(WineRepositoryImpl wineRepositoryI, UserRepositoryI userRepository, RatingServiceImpl ratingService){
        super();
        this.wineRepositoryI = wineRepositoryI;
        this.userRepository = userRepository;
        this.ratingService = ratingService;
    }

    @GetMapping("/topwines")
    public List<WineProjectionI> getTopWines(){
        return wineRepositoryI.findWinesWithAverageRatings();
    }

    @GetMapping("/allwines")
    public List<Wine> getAllWines() { return wineRepositoryI.findAll();}

    @GetMapping("/ratings/{rating_id}")
    public Rating getRating_by_id(@PathVariable("rating_id") String rating_id) {

        try {
            return ratingService.getRating_by_id_safe(Long.parseLong(rating_id));
        } catch (Exception e){
            // no parsable input
            return null;
        }

    }


    @PostMapping("/ratings")
    public String addRating(@RequestBody Rating rating) {

        // get user from database
        Optional<User> user = userRepository.findById(rating.getUser().getId());
        // set user in rating
        rating.setUser(user.get());
        // save rating
        ratingService.saveRating(rating);

        return "Rating added successfully";
    }

    @PutMapping("/ratings")
    public String updateRating(@RequestBody Rating rating) {

        // get user from database
        Optional<User> user = userRepository.findById(rating.getUser().getId());

        // set user in rating
        rating.setUser(user.get());

        // save rating
        ratingService.updateRating(rating);

        return "Rating updated successfully";
    }


    @GetMapping("/ratingsByUser/{user_id}")
    public List<Rating> getRatingsByUser(@PathVariable("user_id") String userId) {

        try {
            return ratingService.getRatingsByUser_Safe(Long.parseLong(userId));
        } catch (Exception e){
            // possibly do error handling
            return null;
        }

    }




}
