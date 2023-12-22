package com.winewizard.winewizard.service;

import com.winewizard.winewizard.model.Rating;

import java.util.List;

public interface RatingServiceI {

    List<Rating> getAllRatings();

    Rating saveRating(Rating rating);

}
