package com.udacity.vehicles.client.maps;

/**
 * Declares a class to store an address, city, state and zip code.
 */

public class Address {
    private String streetAndNumber;
    private String city;
    private String state;
    private String zip;

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public Address setStreetAndNumber(String streetAndNumber) {
        this.streetAndNumber = streetAndNumber;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public Address setState(String state) {
        this.state = state;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public Address setZip(String zip) {
        this.zip = zip;
        return this;
    }
}
