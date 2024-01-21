package com.winewizard.winewizard.repository;

public interface StatsProjectionI {
    // This is an interface to be used as a projection for pulling statistics out of the database.

    int getNumRatings();
    int getNumwines();
    int getNumwinery();
    int getNumuser();

}

