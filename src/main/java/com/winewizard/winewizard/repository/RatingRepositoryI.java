package com.winewizard.winewizard.repository;

import com.winewizard.winewizard.model.Rating;
import com.winewizard.winewizard.model.WineDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepositoryI extends JpaRepository<Rating, Long> {

    void deleteByUserId(Long user_id);

    @Query(value =
            "SELECT w.NAME, AVG(r.taste_rating) AS avg_taste_rating, AVG(r.design_rating) AS avg_design_rating,AVG(r.price_rating) AS avg_price_rating" +
                    "            FROM WINE w JOIN RATINGS r ON w.ID = r.wine_id" +
                    "            GROUP BY w.ID, w.NAME" +
                    "            ORDER BY avg_taste_rating DESC", nativeQuery = true)
    List<WineDTO> findWinesWithAverageRatings();

}
