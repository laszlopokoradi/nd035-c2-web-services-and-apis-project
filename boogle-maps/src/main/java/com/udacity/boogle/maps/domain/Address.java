package com.udacity.boogle.maps.domain;


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
