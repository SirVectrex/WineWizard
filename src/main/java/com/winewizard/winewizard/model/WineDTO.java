package com.winewizard.winewizard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class WineDTO {

    private String name;
    private Float avgTasteRating;
    private Double avgDesignRating;
    private Double avgPriceRating;
    @Id
    private Long id;


    public WineDTO() {

    }

    public WineDTO(String name) {
        this.name = name;
    }

    public WineDTO(String name, Float avgTasteRating) {
        this.name = name;
        this.avgTasteRating = avgTasteRating;
    }

    public WineDTO(String name, Float avgTasteRating, Double avgDesignRating) {
        this.name = name;
        this.avgTasteRating = avgTasteRating;
        this.avgDesignRating = avgDesignRating;
    }

    public WineDTO(String name, Float avgTasteRating, Double avgDesignRating, Double avgPriceRating) {
        this.name = name;
        this.avgTasteRating = avgTasteRating;
        this.avgDesignRating = avgDesignRating;
        this.avgPriceRating = avgPriceRating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getavgTasteRating() {
        return avgTasteRating;
    }

    public void setavgTasteRating(Float avgTasteRating) {
        this.avgTasteRating = avgTasteRating;
    }

    public Double getAvgDesignRating() {
        return avgDesignRating;
    }

    public void setAvgDesignRating(Double avgDesignRating) {
        this.avgDesignRating = avgDesignRating;
    }

    public Double getAvgPriceRating() {
        return avgPriceRating;
    }

    public void setAvgPriceRating(Double avgPriceRating) {
        this.avgPriceRating = avgPriceRating;
    }

    // ... getters and setters ...
}
