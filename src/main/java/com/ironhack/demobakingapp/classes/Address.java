package com.ironhack.demobakingapp.classes;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String city;
    private String direction;
    private String state;
    private String country;
    private String postalCode;

    public Address() {
    }

    public Address(String city, String direction, String state, String country, String postalCode) {
        this.city = city;
        this.direction = direction;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
