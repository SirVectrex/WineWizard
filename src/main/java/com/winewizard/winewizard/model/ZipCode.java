package com.winewizard.winewizard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="zipcodes")
public class ZipCode {
    @Id
    @Column(name="zip_code")
    String zipCode;
    @Column(name="country_code")

    String countryCode;
    String city;
    String state;

    public ZipCode(String zipCode, String countryCode, String city, String state) {
        this.zipCode = zipCode;
        this.countryCode = countryCode;
        this.city = city;
        this.state = state;
    }

    public ZipCode() {
    }

    @Override
    public String toString() {
        return zipCode + ", " + countryCode + ", " + state + ", " + city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
