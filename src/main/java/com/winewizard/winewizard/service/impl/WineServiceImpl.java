package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.repository.WineRepository;
import com.winewizard.winewizard.service.WineServiceI;
import org.springframework.stereotype.Service;

import java.util.List;

// Provides the methods which we can use to interact with the database
@Service
public class WineServiceImpl implements WineServiceI {

    // Provides the most important CRUD-Functions
    private WineRepository wineRepository;

    public WineServiceImpl(WineRepository wineRepository){
        super();
        this.wineRepository = wineRepository;
    }

    @Override
    public List<Wine> getAllWines() {
        return wineRepository.findAll();
    }

    @Override
    public Wine saveWine(Wine wine) {
        return wineRepository.save(wine);
    }
}
