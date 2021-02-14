package com.ironhack.demobakingapp.controller.DTO.Users;

import javax.validation.constraints.NotNull;

public class AddressDTO {
    /** PARAMS **/
    @NotNull(message = "You need to provide a primary address")
    String city;
    @NotNull(message = "You need to provide a primary address")
    String direction;
    @NotNull(message = "You need to provide a primary address")
    String state;
    @NotNull(message = "You need to provide a primary address")
    String country;
    @NotNull(message = "You need to provide a primary address")
    String postalCode;

    /** CONSTRUCTORS **/
    public AddressDTO() {
    }

    public AddressDTO(@NotNull(message = "You need to provide a primary address") String city, @NotNull(message = "You need to provide a primary address") String direction, @NotNull(message = "You need to provide a primary address") String state, @NotNull(message = "You need to provide a primary address") String country, @NotNull(message = "You need to provide a primary address") String postalCode) {
        setCity(city);
        setDirection(direction);
        setState(state);
        setCountry(country);
        setPostalCode(postalCode);
    }

    /** GETTERS & SETTERS **/

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
