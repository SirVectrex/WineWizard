package com.winewizard.winewizard.repository.impl;

import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.repository.RecommendationProjectionI;
import com.winewizard.winewizard.repository.StatsProjectionI;
import com.winewizard.winewizard.repository.WineProjectionI;
import com.winewizard.winewizard.repository.WineRepositoryI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "Select (Select Count(*) from RATING) as numratings, (Select Count(*) from WINE) as numwines,  (Select Count(*) from WINERY) as numwinery, (Select Count(*) from \"USER\") as numuser", nativeQuery = true)
    List<StatsProjectionI> getStats();

    @Query(value = "SELECT w.NAME as name, ROUND(AVG(r.TASTE_RATING), 2) as avgTasteRating, ROUND(AVG(r.DESIGN_RATING), 2) as avgDesignRating, ROUND(AVG(r.PRICE_RATING), 2) as avgPriceRating " +
            "FROM \"USER\" u INNER JOIN RATING r on u.ID = r.USER_ID " +
            "INNER JOIN WINE w ON r.WINE_ID = w.ID WHERE u.ZIP_CODE = 93333 " +
            "GROUP BY w.NAME " +
            "ORDER BY AVG(r.TASTE_RATING) DESC", nativeQuery = true)
    List<WineProjectionI> getWinesByZipCode();

    @Query(value = "SELECT w.NAME as name, ROUND(AVG(r.TASTE_RATING), 2) as avgTasteRating, ROUND(AVG(r.DESIGN_RATING), 2) as avgDesignRating, ROUND(AVG(r.PRICE_RATING), 2) as avgPriceRating " +
            "FROM \"USER\" u INNER JOIN RATING r on u.ID = r.USER_ID " +
            "INNER JOIN WINE w ON r.WINE_ID = w.ID WHERE u.ZIP_CODE = :zipcode " +
            "GROUP BY w.NAME " +
            "ORDER BY AVG(r.TASTE_RATING) DESC", nativeQuery = true)
    Page<WineProjectionI> getWinesByZipCodewParamwPage(@Param("zipcode") int zipcode, Pageable pageable);

    @Query(value = "SELECT w.type as TopType " +
            "FROM RATING r " +
            "join WINE w on r.WINE_ID = w.ID " +
            "where r.USER_ID = :id " +
            "group by w.type " +
            "order by sum(r.DESIGN_RATING + r.PRICE_RATING + r.TASTE_RATING) desc " +
            "limit 3", nativeQuery = true)
    List<RecommendationProjectionI> getTopWineTypesByUser(@Param("id") long id);

    @Query(value = "SELECT  id, type, name, bookmarked, ean, winery_id, description FROM ( " +
            "SELECT sum(r.DESIGN_RATING + r.PRICE_RATING + r.TASTE_RATING) / count(*) as avg_rating, " +
            "w.id, " +
            "w.type, " +
            "w.name, " +
            "w.bookmarked, " +
            "w.ean, " +
            "w.winery_id, " +
            "w.description " +
            "FROM RATING r " +
            "JOIN WINE w on r.WINE_ID = w.id " +
            "where w.type in :types " +
            "group by wine_id, w.type, w.name,  w.bookmarked, w.ean, w.winery_id, w.description " +
            "order by avg_rating desc "  +
            "Limit 5)", nativeQuery = true)
    List<Wine> getWineRecommendations(@Param("types") List<String> types);
}
