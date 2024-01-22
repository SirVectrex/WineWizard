package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.model.Rating;
import com.winewizard.winewizard.repository.RatingRepositoryI;
import com.winewizard.winewizard.service.RatingServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingServiceI {

    // Provides the most important CRUD-Functions
    RatingRepositoryI ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepositoryI ratingRepository){
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> getAllRatings() {
        return (List<Rating>) ratingRepository.findAll();
    }

    @Override
    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Transactional
    public void deleteRatingsByUserId(Long user_id) {
        ratingRepository.deleteByUserId(user_id);
    }

    public List<Rating> getAllRatingsByUserId(Long userId){
        return ratingRepository.findAllByUserId(userId);
    }

    public Page<Rating> getAllRatingsByUserID(Pageable pageable, Long userId){
        return ratingRepository.findAllByUserId(userId, pageable);
    }

    public Rating getRating_by_id_safe(long rating_id){

        // get rating from Service
        Rating rating = findRatingById(Long.valueOf(rating_id));

        if(rating != null){
            // remove user object in rating for security reasons
            rating.getUser().setPassword(null);

            // set user.role to null for security reasons
            rating.getUser().setRoles(null);
        }
        return rating;
    }

    public List<Rating> getRatingsByUser_Safe(long user_id){

        List<Rating> ratings = getAllRatingsByUserId(Long.valueOf(user_id));

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

    @Override
    public void deleteRatingById(Long id) {
        ratingRepository.deleteById(id);
    }

    @Override
    public Rating findRatingById(Long id) {
        return ratingRepository.findById(id).orElse(null);
    }

    @Override
    public void updateRating(Rating rating) {
        ratingRepository.save(rating);
    }

    ;



}
