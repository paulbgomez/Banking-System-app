package com.ironhack.demobakingapp.model;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.enums.Status;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Repository
@PrimaryKeyJoinColumn(name = "id") //este es el id de Account
public class Savings extends Account{

    @Enumerated(EnumType.STRING)
    private Status status;
    private Money minimumBalanceAccount;
    private BigDecimal interestRate;
    private String secretKey;

    public Savings() {
    }

    public Savings(Status status, Money minimumBalanceAccount, BigDecimal interestRate, String secretKey) {
        this.status = status;
        this.minimumBalanceAccount = minimumBalanceAccount;
        this.interestRate = interestRate;
        this.secretKey = secretKey;
    }

    public Savings(Money balanceAmount, Money penaltyAmount, AccountHolder primaryOwner, AccountHolder secondaryOwner, List<Transference> sentTransference, List<Transference> receivedTransference, Status status, Money minimumBalanceAccount, BigDecimal interestRate, String secretKey) {
        super(balanceAmount, penaltyAmount, primaryOwner, secondaryOwner, sentTransference, receivedTransference);
        this.status = status;
        this.minimumBalanceAccount = minimumBalanceAccount;
        this.interestRate = interestRate;
        this.secretKey = secretKey;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Money getMinimumBalanceAccount() {
        return minimumBalanceAccount;
    }

    public void setMinimumBalanceAccount(Money minimumBalanceAccount) {
        this.minimumBalanceAccount = minimumBalanceAccount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
