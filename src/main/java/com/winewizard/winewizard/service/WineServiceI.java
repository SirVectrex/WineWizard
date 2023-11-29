package com.winewizard.winewizard.service;

import com.winewizard.winewizard.model.Wine;

import java.util.List;

// Interface to outline the implementation
public interface WineServiceI {
    List<Wine> getAllWines();

    Wine saveWine(Wine wine);

}
