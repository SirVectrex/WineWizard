package com.winewizard.winewizard.repository;

import com.winewizard.winewizard.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepositoryI extends JpaRepository<Rating, Long> {

    void deleteByUserId(Long user_id);
}
