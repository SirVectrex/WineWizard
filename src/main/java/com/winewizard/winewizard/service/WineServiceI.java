package com.winewizard.winewizard.service;

import com.winewizard.winewizard.model.Rating;
import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.model.Winery;
import com.winewizard.winewizard.repository.WineProjectionI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

// Interface to outline the implementation
public interface WineServiceI {
    List<Wine> getAllWines();

    Wine saveWine(Wine wine);

    List<WineProjectionI> getWineRatings();

    Page<Wine> getAllWinesOfWinery(Winery winery, Pageable pageable);

    public Wine findWine(String query);

    public Wine saveWine(Long ean, String name, String description);
}
