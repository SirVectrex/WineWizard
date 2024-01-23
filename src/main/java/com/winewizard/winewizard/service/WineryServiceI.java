package com.winewizard.winewizard.service;

import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.model.Winery;

import java.util.List;

// Interface to outline the implementation
public interface WineryServiceI {
    List<Winery> getAllWineries();

    Winery saveWinery(Winery winery);

    Winery update(Winery winery);

    Winery getByUrlIdent(String ident);

    Winery getByWineryByWineryOwnerName(String name);

}
