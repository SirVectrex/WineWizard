package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.model.Winery;
import com.winewizard.winewizard.repository.WineryRepositoryI;
import com.winewizard.winewizard.service.WineryServiceI;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

// Provides the methods which we can use to interact with the database
@Service
public class WineryServiceImpl implements WineryServiceI {

    // Provides the most important CRUD-Functions
    private final WineryRepositoryI wineryRepository;

    public WineryServiceImpl(WineryRepositoryI wineRepository){
        super();
        this.wineryRepository = wineRepository;
    }

    @Override
    public List<Winery> getAllWineries() {
        return (List<Winery>) wineryRepository.findAll();
    }

    @Override
    public Winery saveWinery(Winery winery) {
        return wineryRepository.save(winery);
    }

    @Override
    public Winery update(Winery winery) {
        return wineryRepository.save(winery);
    }

    @Override
    public void deleteWineryById(Long id) {
         wineryRepository.deleteById(id);
    }


    @Override
    public Winery getByUrlIdent(String ident) {
        return wineryRepository.findByUrlIdentifier(UUID.fromString(ident));
    }

    @Override
    public Winery getByWineryByWineryOwnerName(String name) {
        return wineryRepository.findWineryByWineryOwnerName(name);
    }


}
