package com.ironhack.demobakingapp.model;

import com.ironhack.demobakingapp.classes.Money;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Repository
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected Money balanceAmount;
    protected Money penaltyAmount;
    @ManyToOne(optional = false)
    protected AccountHolder primaryOwner;
    @ManyToOne(optional = true)
    protected AccountHolder secondaryOwner; //This is optional

    @OneToMany(mappedBy = "originAccount")
    protected List<Transference> sentTransference;
    @OneToMany(mappedBy = "destinationAccount")
    protected List<Transference> receivedTransference;

    /**
     * Constructors
     **/
    public Account() {
    }

    public Account(Money balanceAmount, Money penaltyAmount, AccountHolder primaryOwner, AccountHolder secondaryOwner, List<Transference> sentTransference, List<Transference> receivedTransference) {
        this.balanceAmount = balanceAmount;
        this.penaltyAmount = penaltyAmount;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.sentTransference = sentTransference;
        this.receivedTransference = receivedTransference;
    }

/**
     * Getters & Setters
     **/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Money getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Money balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Money getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(Money penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public List<Transference> getSentTransference() {
        return sentTransference;
    }

    public void setSentTransference(List<Transference> sentTransference) {
        this.sentTransference = sentTransference;
    }

    public List<Transference> getReceivedTransference() {
        return receivedTransference;
    }

    public void setReceivedTransference(List<Transference> receivedTransference) {
        this.receivedTransference = receivedTransference;
    }
}

