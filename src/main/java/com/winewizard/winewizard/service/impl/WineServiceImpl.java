package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.model.Winery;
import com.winewizard.winewizard.repository.WineProjectionI;
import com.winewizard.winewizard.repository.WineRepositoryI;
import com.winewizard.winewizard.repository.impl.WineRepositoryImpl;
import com.winewizard.winewizard.service.WineServiceI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

// Provides the methods which we can use to interact with the database
@Service
public class WineServiceImpl implements WineServiceI {

    // Provides the most important CRUD-Functions
    private WineRepositoryImpl wineRepositoryI;

    public WineServiceImpl(WineRepositoryImpl wineRepositoryI){
        super();
        this.wineRepositoryI = wineRepositoryI;
    }

    public List<WineProjectionI> getWineRatings() {
        return wineRepositoryI.findWinesWithAverageRatings();
    }

    @Override
    public List<Wine> getAllWinesOfWinery(Winery winery) {
        return wineRepositoryI.getWinesByWinery( winery);
    }

    public Page<WineProjectionI> getWineRatings(Pageable pageable) {
        return wineRepositoryI.findWinesWithAverageRatings_Pages(pageable);
    }

    public Wine findWine(String query){

        Long ean;
        Wine wine = wineRepositoryI.findByNameContainingIgnoreCase(query);

        if (wine == null) {
            // try to find wine by EAN
            try {
                wine = wineRepositoryI.findByEan(Long.parseLong(query));
            } catch (NumberFormatException e) {
                // handle error
            }

        }

        return wine;
    }

    @Override
    public List<Wine> getAllWines() {
        return wineRepositoryI.findAll();
    }

    @Override
    public Wine saveWine(Wine wine) {
        return wineRepositoryI.save(wine);
    }

    public Wine saveWine(Long ean, String name, String description){
    Wine wine = new Wine();
        wine.setEan(ean);
        wine.setName(name);
        wine.setDescription(description);
        wineRepositoryI.save(wine);

        return  wine;

    }
}
