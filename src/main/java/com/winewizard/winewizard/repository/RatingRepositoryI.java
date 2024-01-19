package com.winewizard.winewizard.repository;

import com.winewizard.winewizard.model.Rating;
import com.winewizard.winewizard.model.WineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepositoryI extends JpaRepository<Rating, Long> {

    void deleteByUserId(Long user_id);

    List<Rating> findAllByUserId(Long userId);

    Page<Rating> findAllByUserId(Long userID, Pageable pageable);
}
