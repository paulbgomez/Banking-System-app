package com.ironhack.demobakingapp.model.Accounts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.enums.Status;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import org.hibernate.bytecode.internal.bytebuddy.BytecodeProviderImpl;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Savings extends Account{

    /** PARAMS **/

    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
    })
    private Money minimumBalance;
    private BigDecimal interestRate;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastFee;
    private boolean belowMinimumBalance;
    private String secretKey;

    /** CONSTRUCTORS **/

    public Savings() {
    }

    public Savings(AccountHolder primaryOwner, Money balance, String secretKey) {
        super(balance, primaryOwner);
        setBelowMinimumBalance(false);
        setSecretKey(secretKey);
    }

    /** GETTERS & SETTERS **/

    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDateTime getLastFee() {
        return lastFee;
    }

    public void setLastFee(LocalDateTime lastFee) {
        this.lastFee = lastFee;
    }

    public boolean isBelowMinimumBalance() {
        return belowMinimumBalance;
    }

    public void setBelowMinimumBalance(boolean belowMinimumBalance) {
        this.belowMinimumBalance = belowMinimumBalance;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
