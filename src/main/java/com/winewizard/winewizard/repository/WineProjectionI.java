package com.winewizard.winewizard.repository;

public interface WineProjectionI {
    // projection interface tof pulling wine information from the database
    String getName();
    Double getAvgTasteRating();
    Double getAvgDesignRating();
    Double getAvgPriceRating();
}
