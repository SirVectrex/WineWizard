package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.model.Rating;
import com.winewizard.winewizard.repository.RatingRepositoryI;
import com.winewizard.winewizard.service.RatingServiceI;
import org.springframework.stereotype.Service;

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
}
