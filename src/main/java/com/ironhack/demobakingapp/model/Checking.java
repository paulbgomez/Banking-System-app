package com.ironhack.demobakingapp.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Checking extends StudentChecking{

    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
    })
    private final Money minimumBalance = new Money(new BigDecimal(250));
    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_fee_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "monthly_fee_currency"))
    })
    private final Money monthlyFee = new Money(new BigDecimal(12)) ;
    private boolean belowMinimumBalance;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastFee;

    public Checking() {
    }

    public Checking(Money balance, AccountHolder primaryOwner, Status status, String secretKey) {
        super(balance, primaryOwner, status, secretKey);
        setBelowMinimumBalance(false);
        setLastFee(LocalDateTime.now());
    }

    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public Money getMonthlyFee() {
        return monthlyFee;
    }

    public boolean isBelowMinimumBalance() {
        return belowMinimumBalance;
    }

    public void setBelowMinimumBalance(boolean belowMinimumBalance) {
        this.belowMinimumBalance = belowMinimumBalance;
    }

    public LocalDateTime getLastFee() {
        return lastFee;
    }

    public void setLastFee(LocalDateTime lastFee) {
        this.lastFee = lastFee;
    }
}

