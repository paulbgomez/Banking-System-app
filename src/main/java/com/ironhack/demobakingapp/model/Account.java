package com.ironhack.demobakingapp.model;

import com.ironhack.demobakingapp.classes.Money;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "balance_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "balance_currency"))
    })
    protected Money balance;
    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "penalty_fee_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "penalty_fee_currency"))
    })
    protected final Money penalty = new Money(new BigDecimal(40));
    @ManyToOne(optional = false)
    protected AccountHolder primaryOwner;
    @ManyToOne(optional = true)
    protected AccountHolder secondaryOwner; //This is optional

    @OneToMany(mappedBy = "senderAccount")
    protected List<Movement> sentMoney;
    @OneToMany(mappedBy = "receiverAccount")
    protected List<Movement> receivedMoney;

    /**
     * Constructors
     **/
    public Account() {
    }

    public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
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

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public Money getPenalty() {
        return penalty;
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

    public List<Movement> getSentMoney() {
        return sentMoney;
    }

    public void setSentMoney(List<Movement> sentMoney) {
        this.sentMoney = sentMoney;
    }

    public List<Movement> getReceivedMoney() {
        return receivedMoney;
    }

    public void setReceivedMoney(List<Movement> receivedMoney) {
        this.receivedMoney = receivedMoney;
    }
}

