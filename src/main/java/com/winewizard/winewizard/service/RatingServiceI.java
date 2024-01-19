package com.winewizard.winewizard.service;

import com.winewizard.winewizard.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RatingServiceI {

    List<Rating> getAllRatings();

    Rating saveRating(Rating rating);

    void deleteRatingsByUserId(Long user_id);

    List<Rating> getAllRatingsByUserId(Long userId);

    void deleteRatingById(Long id);

    Rating findRatingById(Long id);

    void updateRating(Rating rating);

    Page<Rating> getAllRatingsByUserID(Pageable pageable, Long userid);
}
