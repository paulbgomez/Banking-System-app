package com.ironhack.demobakingapp.model.Accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.enums.Status;
import com.ironhack.demobakingapp.model.Movement;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {
    /** PARAMS **/
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
    protected AccountHolder secondaryOwner;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    protected LocalDateTime creationTime;

    protected boolean isFrozen;

    @Enumerated(EnumType.STRING)
    protected Status status;

    @OneToMany(mappedBy = "senderAccount")
    @JsonIgnore
    protected List<Movement> sentMoney;
    @OneToMany(mappedBy = "receiverAccount")
    @JsonIgnore
    protected List<Movement> receivedMoney;

    /** CONSTRUCTORS **/
    public Account() {
    }

    public Account(Money balance, AccountHolder primaryOwner) {
        setBalance(balance);
        setPrimaryOwner(primaryOwner);
        setCreationTime(LocalDateTime.now());
        setFrozen(false);
        setStatus(Status.ACTIVE);
    }


    /** GETTERS & SETTERS **/
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

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

    public void blockAccount(){
        if(isFrozen){
            this.setStatus(Status.FROZEN);
        }
    }
}

