package com.ironhack.demobakingapp.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ironhack.demobakingapp.classes.Address;

import javax.persistence.*;
import java.time.LocalDate;

import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class AccountHolder extends User {

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;

    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "city", column = @Column(name = "mailing_address_city")),
            @AttributeOverride(name = "direction", column = @Column(name = "mailing_address_direction")),
            @AttributeOverride(name = "state", column = @Column(name = "mailing_address_state")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "mailing_address_postal_code"))
    })
    private Address mailingAddress;
    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "city", column = @Column(name = "primary_address_city")),
            @AttributeOverride(name = "direction", column = @Column(name = "primary_address_direction")),
            @AttributeOverride(name = "state", column = @Column(name = "primary_address_state")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "primary_address_postal_code"))
    })
    private Address primaryAddress;
    @OneToMany(mappedBy = "primaryOwner")
    private Set<Account> primaryAccounts;
    @OneToMany(mappedBy = "secondaryOwner")
    private Set<Account> secondaryAccounts;

    public AccountHolder() {
    }

    public AccountHolder(String name, String password, String userName, LocalDate birthDate, Address mailingAddress, Address primaryAddress, Set<Account> primaryAccounts, Set<Account> secondaryAccounts) {
        super(name, password, userName);
        this.birthDate = birthDate;
        this.mailingAddress = mailingAddress;
        this.primaryAddress = primaryAddress;
        this.primaryAccounts = primaryAccounts;
        this.secondaryAccounts = secondaryAccounts;
    }

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

    public Set<Account> getPrimaryAccounts() {
        return primaryAccounts;
    }

    public void setPrimaryAccounts(Set<Account> primaryAccounts) {
        this.primaryAccounts = primaryAccounts;
    }

    public Set<Account> getSecondaryAccounts() {
        return secondaryAccounts;
    }

    public void setSecondaryAccounts(Set<Account> secondaryAccounts) {
        this.secondaryAccounts = secondaryAccounts;
    }
}
