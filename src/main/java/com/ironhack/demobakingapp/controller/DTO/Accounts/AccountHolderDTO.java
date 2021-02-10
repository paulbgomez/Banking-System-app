package com.ironhack.demobakingapp.controller.DTO.Accounts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ironhack.demobakingapp.classes.Address;
import com.ironhack.demobakingapp.controller.DTO.Users.UserDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


public class AccountHolderDTO extends UserDTO {

    /** PARAMS **/
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;
    private Address mailingAddress;
    private Address primaryAddress;

    /** CONSTRUCTORS **/
    public AccountHolderDTO(@NotEmpty @NotNull String name, @NotEmpty @NotNull String username, @NotEmpty @NotNull String password, LocalDate birthDate, @NotEmpty @NotNull Address mailingAddress, @NotEmpty @NotNull Address primaryAddress) {
        super(name, username, password);
        setBirthDate(birthDate);
        setMailingAddress(mailingAddress);
        setPrimaryAddress(primaryAddress);
    }

    /** GETTERS & SETTERS **/
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }


}
