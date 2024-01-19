package com.winewizard.winewizard.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name="winery")
public class Winery implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String wineryName;

    private Long wineryOwnerId;

    @GeneratedValue
    private UUID urlIdentifier;

    //@OneToOne(cascade = CascadeType.ALL)
   // @JoinColumn(name = "address_id", referencedColumnName = "id")
   // private Address address;

    @OneToMany
    private Collection<Wine> wines;

    @PrePersist
    protected void onCreate() {
        setUrlIdentifier(java.util.UUID.randomUUID());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWineryName() {
        return wineryName;
    }

    public void setWineryName(String name) {
        this.wineryName = name;
    }

    public UUID getUrlIdentifier() {
        return urlIdentifier;
    }

    public String getShareLink() {
        return "http://localhost:8080/winery/profile/" + getUrlIdentifier();
    }

    public void setUrlIdentifier(UUID urlIdentifier) {
        if(this.urlIdentifier != null){
            System.out.println("Invalid operation: URL Identifier can not be overwritten");
            return;
        }

        this.urlIdentifier = urlIdentifier;
    }

    //public Address getAddress() {
     //   return address;
    //}

    //public void setAddress(Address address) {
     //   this.address = address;
    //}

    public Collection<Wine> getWines() {
        return wines;
    }

    public void setWines(Collection<Wine> wines) {
        this.wines = wines;
    }

    public Long getWineryOwnerId() {
        return wineryOwnerId;
    }

    public void setWineryOwnerId(Long wineryOwnerId) {
        this.wineryOwnerId = wineryOwnerId;
    }
}
