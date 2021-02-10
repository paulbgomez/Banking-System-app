package com.ironhack.demobakingapp.model;

import com.ironhack.demobakingapp.classes.Money;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CreditCard extends Account{

    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "credit_limit_currency"))
    })
    private Money creditLimit;
    private BigDecimal interestRate;

    public CreditCard() {
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, Money creditLimit, BigDecimal interestRate) {
        super(balance, primaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public Money getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Money creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
