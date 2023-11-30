package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.model.Winery;
import com.winewizard.winewizard.repository.WineRepository;
import com.winewizard.winewizard.repository.WineryRepository;
import com.winewizard.winewizard.service.WineServiceI;
import com.winewizard.winewizard.service.WineryServiceI;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

// Provides the methods which we can use to interact with the database
@Service
public class WineryServiceImpl implements WineryServiceI {

    // Provides the most important CRUD-Functions
    private WineryRepository wineryRepository;

    public WineryServiceImpl(WineryRepository wineRepository){
        super();
        this.wineryRepository = wineRepository;
    }

    @Override
    public List<Winery> getAllWineries() {
        return wineryRepository.findAll();
    }

    @Override
    public Winery saveWinery(Winery winery) {
        return wineryRepository.save(winery);
    }

    @Override
    public Winery getByUrlIdent(String ident) {
        return wineryRepository.findByUrlIdentifier(UUID.fromString(ident));
    }
}
