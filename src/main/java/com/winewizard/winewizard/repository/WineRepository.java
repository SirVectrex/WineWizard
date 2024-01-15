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

    @Query("SELECT w.name as name, AVG(r.ratingTaste) as avgTasteRating, AVG(r.ratingDesign) as avgDesignRating, AVG(r.ratingPrice) as avgPriceRating " +
            "FROM Wine w JOIN Rating r ON w = r.wine " +
            "GROUP BY w.id, w.name " +
            "ORDER BY AVG(r.ratingTaste) DESC")
    List<WineProjectionI> findWinesWithAverageRatings();

    @Query("SELECT w.name as name, ROUND(AVG(r.ratingTaste), 2) as avgTasteRating, ROUND(AVG(r.ratingDesign), 2) as avgDesignRating, ROUND(AVG(r.ratingPrice), 2) as avgPriceRating " +
            "FROM Wine w JOIN Rating r ON w = r.wine " +
            "GROUP BY w.id, w.name " +
            "ORDER BY AVG(r.ratingTaste) DESC")
    Page<WineProjectionI> findWinesWithAverageRatings_Pages(Pageable pageable);





}
