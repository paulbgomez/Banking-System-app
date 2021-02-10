package com.ironhack.demobakingapp.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ironhack.demobakingapp.classes.Money;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {
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

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    protected LocalDateTime creationTime;

    @OneToMany(mappedBy = "senderAccount")
    protected List<Movement> sentMoney;
    @OneToMany(mappedBy = "receiverAccount")
    protected List<Movement> receivedMoney;

    /**
     * Constructors
     **/
    public Account() {
    }

    public Account(Money balance, AccountHolder primaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.creationTime = LocalDateTime.now();
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
