package com.winewizard.winewizard.controller;

import com.winewizard.winewizard.model.Bookmark;
import com.winewizard.winewizard.model.Rating;
import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.repository.UserRepositoryI;
import com.winewizard.winewizard.repository.WineProjectionI;
import com.winewizard.winewizard.repository.impl.WineRepositoryImpl;
import com.winewizard.winewizard.service.RatingServiceI;
import com.winewizard.winewizard.service.impl.BookmarkServiceImpl;
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

    BookmarkServiceImpl bookmarkService;

    public API(WineRepositoryImpl wineRepositoryI, UserRepositoryI userRepository, RatingServiceImpl ratingService, BookmarkServiceImpl bookmarkService){
        super();
        this.wineRepositoryI = wineRepositoryI;
        this.userRepository = userRepository;
        this.ratingService = ratingService;
        this.bookmarkService = bookmarkService;
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

        System.out.println("Attempt to add rating");

        // get user from database
        //Optional<User> user = userRepository.findById(rating.getUser().getId());
        // set user in rating
        //rating.setUser(user.get());
        // save rating
        ratingService.saveRating(rating);
        /*
        {
            "user": {
            "id": 1
            },
            "wine": {
            "id": 4
            },
            "ratingTaste": 1,
            "ratingDesign": 1,
            "ratingPrice": 1
        }

         */

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

    @DeleteMapping("/ratings/{rating_id}")
    public String deleteRating(@PathVariable("rating_id") String rating_id) {

        try {
            ratingService.deleteRatingById(Long.parseLong(rating_id));
        } catch (Exception e){
            // possibly do error handling
            return "Rating not found";
        }

        return "Rating deleted successfully";
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

    @PutMapping("/bookmarks")
    public String updateBookmark(@RequestBody Bookmark bookmark) {


        // get user from database
        Optional<User> user = userRepository.findById(bookmark.getUser().getId());

        // set user in rating
        bookmark.setUser(user.get());

        // save rating
        bookmarkService.updateBookmark(bookmark);

        return "Bookmark updated successfully";
    }


    @PostMapping("/bookmarks")
    public String addBookmark(@RequestBody Bookmark bookmark) {

        bookmarkService.saveBookmark(bookmark);

        return "Bookmark added successfully";
    }

    @DeleteMapping("/bookmarks/{user_id}")
    public String deleteBookmark(@PathVariable("user_id") String user_id) {

        try {
            bookmarkService.deleteBookmarksByUserId(Long.parseLong(user_id));
        } catch (Exception e){
            // possibly do error handling
            return "Rating not found";
        }

        return "Bookmark deleted successfully";
    }


}
