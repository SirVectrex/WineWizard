package com.winewizard.winewizard.repository.impl;

import com.winewizard.winewizard.repository.StatsProjectionI;
import com.winewizard.winewizard.repository.WineProjectionI;
import com.winewizard.winewizard.repository.WineRepositoryI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WineRepositoryImpl extends WineRepositoryI {

    @Query("SELECT w.name as name, ROUND(AVG(r.ratingTaste), 2) as avgTasteRating, ROUND(AVG(r.ratingDesign), 2) as avgDesignRating, ROUND(AVG(r.ratingPrice), 2) as avgPriceRating " +
            "FROM Wine w JOIN Rating r ON w = r.wine " +
            "GROUP BY w.id, w.name " +
            "ORDER BY AVG(r.ratingTaste) DESC")
    List<WineProjectionI> findWinesWithAverageRatings();

    @Query("SELECT w.name as name, ROUND(AVG(r.ratingTaste), 2) as avgTasteRating, ROUND(AVG(r.ratingDesign), 2) as avgDesignRating, ROUND(AVG(r.ratingPrice), 2) as avgPriceRating " +
            "FROM Wine w JOIN Rating r ON w = r.wine " +
            "GROUP BY w.id, w.name " +
            "ORDER BY AVG(r.ratingTaste) DESC")
    Page<WineProjectionI> findWinesWithAverageRatings_Pages(Pageable pageable);

    @Query(value = "Select (Select Count(*) from RATING) as numratings, (Select Count(*) from WINE) as numwines,  (Select Count(*) from WINERY) as numwinery, (Select Count(*) from feedback) as num_feedback", nativeQuery = true)
    List<StatsProjectionI> getStats();



}
