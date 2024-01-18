package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.Rating;
import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.repository.UserRepositoryI;
import com.winewizard.winewizard.repository.WineProjectionI;
import com.winewizard.winewizard.repository.impl.WineRepositoryImpl;
import com.winewizard.winewizard.service.RatingServiceI;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class API {

    WineRepositoryImpl wineRepositoryI;
    UserRepositoryI userRepository;

    RatingServiceI ratingService;

    public API(WineRepositoryImpl wineRepositoryI, UserRepositoryI userRepository, RatingServiceI ratingService){
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

    @PostMapping("/addRating")
    public String addRating(@RequestBody Rating rating) {

        // get user from database
        Optional<User> user = userRepository.findById(rating.getUser().getId());

        // set user in rating
        rating.setUser(user.get());

        // save rating
        ratingService.saveRating(rating);

        return "Rating added successfully";
    }

    @PutMapping("/updateRating")
    public String updateRating(@RequestBody Rating rating) {

        // get user from database
        Optional<User> user = userRepository.findById(rating.getUser().getId());

        // set user in rating
        rating.setUser(user.get());

        // save rating
        ratingService.updateRating(rating);

        return "Rating updated successfully";
    }


    @GetMapping("/deleteRating/{rating_id}")
    public String deleteRating(@PathVariable("rating_id") Long ratingId) {

        Rating rating = ratingService.findRatingById(ratingId);

        System.out.println(rating);

        try {
            ratingService.deleteRatingById(ratingId);
        } catch (Exception e) {
            return "Rating not found";
        }

        return "Rating deleted successfully";
    }


    @GetMapping("/getRating/{rating_id}")
    public Rating getRating(@PathVariable("rating_id") Long ratingId) {

        Rating rating = ratingService.findRatingById(ratingId);

        if (rating == null) {
            // Handle error, such as returning an error message or status code
            return null;
        }
        // remove user object in rating for security reasons
        rating.getUser().setPassword(null);

        return rating;
    }

    @GetMapping("/getRatingsByUser/{user_id}")
    public List<Rating> getRatingsByUser(@PathVariable("user_id") Long userId) {

        System.out.println("LOG: Getting ratings for user with id: " + userId);

        List<Rating> ratings = ratingService.getAllRatingsByUserId(userId);

        // set user password to null for security reasons
        for (Rating rating : ratings) {
            rating.getUser().setPassword(null);
        }

        if (ratings == null) {
            // Handle error, such as returning an error message or status code
            return null;
        }

        return ratings;
    }




}
