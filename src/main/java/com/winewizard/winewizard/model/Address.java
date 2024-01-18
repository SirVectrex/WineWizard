package com.winewizard.winewizard.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="address")
public class Address implements Serializable {
//TODO: is this class required?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

  //  @OneToOne(mappedBy = "address")
   // Winery winery;

    String city;
    String postalCode;
    String street;
    String houseNumber;

}
