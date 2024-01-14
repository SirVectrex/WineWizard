package com.winewizard.winewizard.repository;

import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.model.WineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// This is the access wrapper which is reused in the service implementation
public interface WineRepository extends JpaRepository<Wine, Long> {
    Page<Wine> findByNameContainingIgnoreCase(String searchTerm, Pageable pageable);

    Wine findByNameContainingIgnoreCase(String searchTerm);

    List<Wine> findAllById(Long id);

    @Query(value =
            "SELECT w.NAME, AVG(r.taste_rating) AS avg_taste_rating, AVG(r.design_rating) AS avg_design_rating,AVG(r.price_rating) AS avg_price_rating" +
                    "            FROM WINE w JOIN RATINGS r ON w.ID = r.wine_id" +
                    "            GROUP BY w.ID, w.NAME" +
                    "            ORDER BY avg_taste_rating DESC", nativeQuery = true)
    List<WineDTO> findWinesWithAverageRatings();

}
