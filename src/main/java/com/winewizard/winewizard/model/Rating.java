package com.winewizard.winewizard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

@Entity
@Table(name = "ratings")
public class Rating implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wine_id", nullable = false, referencedColumnName = "id")
    private Wine wine;

    @Column(name = "taste_rating", nullable = false)
    @NotBlank(message = "Taste rating is mandatory")
    private int ratingTaste;

    @Column(name = "design_rating", nullable = false)
    @NotBlank(message = "Design rating is mandatory")
    private int ratingDesign;

    @Column(name = "price_rating", nullable = false)
    @NotBlank(message = "Price rating is mandatory")
    private int ratingPrice;

    public void setId(Long id) {
        this.ratingId = id;
    }


    // Getters and Setters
    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRatingTaste() {
        return ratingTaste;
    }

    public void setRatingTaste(int ratingTaste) {
        this.ratingTaste = ratingTaste;
    }

    public int getRatingDesign() {
        return ratingDesign;
    }

    public void setRatingDesign(int ratingDesign) {
        this.ratingDesign = ratingDesign;
    }

    public int getRatingPrice() {
        return ratingPrice;
    }

    public void setRatingPrice(int ratingPrice) {
        this.ratingPrice = ratingPrice;
    }

    // Constructor, hashCode, equals, toString
}
