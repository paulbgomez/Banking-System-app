package com.ironhack.demobakingapp.model.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ironhack.demobakingapp.classes.Address;
import com.ironhack.demobakingapp.model.Accounts.Account;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class AccountHolder extends User {

    /** PARAMS **/

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;

    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "city", column = @Column(name = "mailing_address_city")),
            @AttributeOverride(name = "direction", column = @Column(name = "mailing_address_direction")),
            @AttributeOverride(name = "state", column = @Column(name = "mailing_address_state")),
            @AttributeOverride(name = "country", column = @Column(name = "mailing_address_country")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "mailing_address_postal_code"))
    })
    private Address mailingAddress;
    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "city", column = @Column(name = "primary_address_city")),
            @AttributeOverride(name = "direction", column = @Column(name = "primary_address_direction")),
            @AttributeOverride(name = "state", column = @Column(name = "primary_address_state")),
            @AttributeOverride(name = "country", column = @Column(name = "primary_address_country")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "primary_address_postal_code"))
    })
    private Address primaryAddress;
    @OneToMany(mappedBy = "primaryOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Account> primaryAccounts;
    @OneToMany(mappedBy = "secondaryOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Account> secondaryAccounts;

    /** CONSTRUCTORS **/

    public AccountHolder() {
    }

    public AccountHolder(String name, String password, String userName, LocalDate birthDate, Address mailingAddress, Address primaryAddress) {
        super(name, password, userName);
        this.birthDate = birthDate;
        this.mailingAddress = mailingAddress;
        this.primaryAddress = primaryAddress;
        this.primaryAccounts = new HashSet<>();
        this.secondaryAccounts = new HashSet<>();
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


    public Set<Account> showAccounts(){
        Set<Account> result = new HashSet<>();
        this.primaryAccounts.stream().map(account->result.add(account)).collect(Collectors.toSet());
        this.secondaryAccounts.stream().map(account->result.add(account)).collect(Collectors.toSet());
        return result;
    }

    @Override
    public String toString() {
        return "AccountHolder{" +
                "birthDate=" + birthDate +
                ", mailingAddress=" + mailingAddress +
                ", primaryAddress=" + primaryAddress +
                ", primaryAccounts=" + primaryAccounts +
                ", secondaryAccounts=" + secondaryAccounts +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                '}';
    }
}
