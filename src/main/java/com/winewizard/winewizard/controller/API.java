package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.Rating;
import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.repository.UserRepositoryI;
import com.winewizard.winewizard.repository.WineProjectionI;
import com.winewizard.winewizard.repository.WineRepository;
import com.winewizard.winewizard.service.RatingServiceI;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class API {

    WineRepository wineRepository;
    UserRepositoryI userRepository;

    RatingServiceI ratingService;

    public API(WineRepository wineRepository, UserRepositoryI userRepository, RatingServiceI ratingService){
        super();
        this.wineRepository = wineRepository;
        this.userRepository = userRepository;
        this.ratingService = ratingService;

    }

    @GetMapping("/topwines")
    public List<WineProjectionI> getTopWines(){
        return wineRepository.findWinesWithAverageRatings();
    }

    @GetMapping("/allwines")
    public List<Wine> getAllWines() { return wineRepository.findAll();}

    @PostMapping("/addRating")
    public String addRating(@RequestParam("user_id") Long userId,
                            @RequestParam("winenumber") Long winenumber,
                            @RequestParam("designrating") int designrating,
                            @RequestParam("pricerating") int pricerating,
                            @RequestParam("tasterating") int tasterating) {

        Optional<Wine> wine = wineRepository.findById(winenumber);
        Optional<User> user = userRepository.findById(userId);

        if (!wine.isPresent() || !user.isPresent()) {
            // Handle error, such as returning an error message or status code
            return "Wine or User not found";
        }

        Rating ratingObject = new Rating();
        ratingObject.setRatingDesign(designrating);
        ratingObject.setRatingPrice(pricerating);
        ratingObject.setRatingTaste(tasterating);
        ratingObject.setWine(wine.get());
        ratingObject.setUser(user.get());

        // Save the rating object using your service or repository
        ratingService.saveRating(ratingObject);
        return "Rating added successfully";
    }

    @PostMapping("/updateRating")
    public String updateRating(@RequestParam("rating_id") Long ratingId,
                               @RequestParam("designrating") int designrating,
                               @RequestParam("pricerating") int pricerating,
                               @RequestParam("tasterating") int tasterating) {

        Rating rating = ratingService.findRatingById(ratingId);

        if (rating == null) {
            // Handle error, such as returning an error message or status code
            return "Rating not found";
        }

        rating.setRatingDesign(designrating);
        rating.setRatingPrice(pricerating);
        rating.setRatingTaste(tasterating);

        // Save the updated rating object using your service or repository
        ratingService.saveRating(rating);

        return "Rating updated successfully";
    }

    @PostMapping("/deleteRating")
    public String deleteRating(@RequestParam("rating_id") Long ratingId) {

        Rating rating = ratingService.findRatingById(ratingId);

        if (rating == null) {
            // Handle error, such as returning an error message or status code
            return "Rating not found";
        }

        // Delete the rating object using your service or repository
        ratingService.deleteRatingById(ratingId);

        return "Rating deleted successfully";
    }

    @GetMapping("/getRating")
    public Rating getRating(@RequestParam("rating_id") Long ratingId) {

        Rating rating = ratingService.findRatingById(ratingId);

        if (rating == null) {
            // Handle error, such as returning an error message or status code
            return null;
        }

        return rating;
    }

    @GetMapping("/getRatingsByUser")
    public List<Rating> getRatingsByUser(@RequestParam("user_id") Long userId) {

        List<Rating> ratings = ratingService.getAllRatingsByUserId(userId);

        if (ratings == null) {
            // Handle error, such as returning an error message or status code
            return null;
        }

        return ratings;
    }




}
