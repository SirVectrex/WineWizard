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

    @GetMapping("/ratings/{rating_id}")
    public Rating getRating_by_id(@PathVariable("rating_id") String rating_id) {

        Rating rating = ratingService.findRatingById(Long.valueOf(rating_id));

        if (rating == null) {
            // Handle error, such as returning an error message or status code
            return null;
        }
        // remove user object in rating for security reasons
        rating.getUser().setPassword(null);

        // set user.role to null for security reasons
        rating.getUser().setRoles(null);

        System.out.println(rating);

        return rating;
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

        System.out.println("LOG: Getting ratings for user with id: " + userId);

        List<Rating> ratings = ratingService.getAllRatingsByUserId(Long.valueOf(userId));

        // set user password and role to null for security reasons
        for (Rating rating : ratings) {
            rating.getUser().setPassword(null);
            rating.getUser().setRoles(null);
        }

        if (ratings == null) {
            // Handle error, such as returning an error message or status code
            return null;
        }

        return ratings;
    }




}
