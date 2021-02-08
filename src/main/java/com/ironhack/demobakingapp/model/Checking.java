package com.ironhack.demobakingapp.model;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.enums.Status;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Checking extends StudentChecking{

    private Money minimumBalanceAccount;
    private BigDecimal monthlyFee;

    public Checking() {
    }

    public Checking(Money balanceAmount, Money penaltyAmount, AccountHolder primaryOwner, AccountHolder secondaryOwner, List<Transference> sentTransference, List<Transference> receivedTransference, Status status, String secretKey, Money minimumBalanceAccount, BigDecimal monthlyFee) {
        super(balanceAmount, penaltyAmount, primaryOwner, secondaryOwner, sentTransference, receivedTransference, status, secretKey);
        this.minimumBalanceAccount = minimumBalanceAccount;
        this.monthlyFee = monthlyFee;
    }

    public Money getMinimumBalanceAccount() {
        return minimumBalanceAccount;
    }

    public void setMinimumBalanceAccount(Money minimumBalanceAccount) {
        this.minimumBalanceAccount = minimumBalanceAccount;
    }

    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(BigDecimal monthlyFee) {
        this.monthlyFee = monthlyFee;
    }
}
