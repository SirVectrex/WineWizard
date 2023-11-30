package com.winewizard.winewizard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

@Entity
@Table(name="wine")
public class Wine implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @ManyToOne
    Winery winery;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
