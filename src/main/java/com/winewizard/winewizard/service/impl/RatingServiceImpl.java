package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.model.Rating;
import com.winewizard.winewizard.repository.RatingRepositoryI;
import com.winewizard.winewizard.service.RatingServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingServiceI {

    // Provides the most important CRUD-Functions
    RatingRepositoryI ratingRepository;

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
