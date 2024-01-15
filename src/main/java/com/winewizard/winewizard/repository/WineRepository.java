package com.winewizard.winewizard.repository;

import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.model.WineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// This is the access wrapper which is reused in the service implementatio
@Repository
public interface WineRepository extends JpaRepository<Wine, Long> {
    Page<Wine> findByNameContainingIgnoreCase(String searchTerm, Pageable pageable);

    Wine findByNameContainingIgnoreCase(String searchTerm);

    List<Wine> findAllById(Long id);

    // String query = "SELECT w.NAME, AVG(r.taste_rating) AS avgTasteRating, AVG(r.design_rating) AS avgDesignRating, AVG(r.price_rating) AS avgPriceRating " +
    //        "FROM WINE w JOIN RATING r ON w.ID = r.wine_id " +
    //        "GROUP BY w.ID, w.NAME ORDER BY avgTasteRating DESC";

    // @Query(value = query, nativeQuery = true)
    //List<WineDTO> findWinesWithAverageRatings();

    @Query("SELECT w.name as name, AVG(r.ratingTaste) as avgTasteRating, AVG(r.ratingDesign) as avgDesignRating, AVG(r.ratingPrice) as avgPriceRating " +
            "FROM Wine w JOIN Rating r ON w = r.wine " +
            "GROUP BY w.id, w.name " +
            "ORDER BY AVG(r.ratingTaste) DESC")
    List<WineProjectionI> findWinesWithAverageRatings();



}
