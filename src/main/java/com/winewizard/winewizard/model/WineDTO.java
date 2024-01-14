package com.winewizard.winewizard.model;

public class WineDTO {

    private String name;
    private Double avgTasteRating;
    private Double avgDesignRating;
    private Double avgPriceRating;

    // Constructor, Getters, and Setters
    public WineDTO(String name, Double avgTasteRating, Double avgDesignRating, Double avgPriceRating) {
        this.name = name;
        this.avgTasteRating = avgTasteRating;
        this.avgDesignRating = avgDesignRating;
        this.avgPriceRating = avgPriceRating;
    }

    // ... getters and setters ...
}
