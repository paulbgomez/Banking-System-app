package com.ironhack.demobakingapp.model;

import com.ironhack.demobakingapp.classes.Money;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CreditCard extends Account{

    private Money creditLimitAmount;
    private BigDecimal interestRate;

    public CreditCard() {
    }

    public CreditCard(Money creditLimitAmount, BigDecimal interestRate) {
        this.creditLimitAmount = creditLimitAmount;
        this.interestRate = interestRate;
    }

    public CreditCard(Money balanceAmount, Money penaltyAmount, AccountHolder primaryOwner, AccountHolder secondaryOwner, List<Transference> sentTransference, List<Transference> receivedTransference, Money creditLimitAmount, BigDecimal interestRate) {
        super(balanceAmount, penaltyAmount, primaryOwner, secondaryOwner, sentTransference, receivedTransference);
        this.creditLimitAmount = creditLimitAmount;
        this.interestRate = interestRate;
    }

    public Money getCreditLimitAmount() {
        return creditLimitAmount;
    }

    public void setCreditLimitAmount(Money creditLimitAmount) {
        this.creditLimitAmount = creditLimitAmount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
