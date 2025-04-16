package com.udacity.boogle.maps.domain;

import lombok.*;

/**
 * Declares a class to store an address, city, state and zip code.
 */
@Getter
@Setter
public class Address {

    private String streetAndNumber;
    private String city;
    private String state;
    private String zip;

    public Address() {
    }

    public Address(String streetAndNumber, String city, String state, String zip) {
        this.streetAndNumber = streetAndNumber;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String toString() {
        return streetAndNumber + ", " + city + " " + state + " " + zip;
    }
}
