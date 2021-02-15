package com.ironhack.demobakingapp.model.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ironhack.demobakingapp.classes.Address;
import com.ironhack.demobakingapp.controller.DTO.Users.AddressDTO;
import com.ironhack.demobakingapp.model.Accounts.Account;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private AddressDTO mailingAddress;
    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "city", column = @Column(name = "primary_address_city")),
            @AttributeOverride(name = "direction", column = @Column(name = "primary_address_direction")),
            @AttributeOverride(name = "state", column = @Column(name = "primary_address_state")),
            @AttributeOverride(name = "country", column = @Column(name = "primary_address_country")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "primary_address_postal_code"))
    })
    private AddressDTO primaryAddress;
    @OneToMany(mappedBy = "primaryOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Account> primaryAccounts;
    @OneToMany(mappedBy = "secondaryOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Account> secondaryAccounts;

    /** CONSTRUCTORS **/

    public AccountHolder() {
    }

    public AccountHolder(String name, String password, String userName, LocalDate birthDate, AddressDTO mailingAddress, AddressDTO primaryAddress) {
        super(name, password, userName);
        setBirthDate(birthDate);
        setMailingAddress(mailingAddress);
        setPrimaryAddress(primaryAddress);
        this.primaryAccounts = new HashSet<>();
        this.secondaryAccounts = new HashSet<>();
    }

    /** METHODS **/
    public List<Account> showAccounts(){
        List<Account> result = new ArrayList<>();
        this.primaryAccounts.stream().map(account->result.add(account)).collect(Collectors.toList());
        this.secondaryAccounts.stream().map(account->result.add(account)).collect(Collectors.toList());
        return result;
    }

    public Boolean isOwner(Long accountID){
        List<Account> allAccounts = this.showAccounts();
        for (Account account: allAccounts){
            if (account.getId().equals(accountID)){
                return true;
            }
        }
        return false;
    }

    /** GETTERS & SETTERS **/

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public AddressDTO getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(AddressDTO mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public AddressDTO getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(AddressDTO primaryAddress) {
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
