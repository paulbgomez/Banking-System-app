package com.ironhack.demobakingapp.controller.DTO.Accounts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreditCardDTO extends AccountDTO{

    /** PARAMS **/

    @NotNull(message = "You need at least one primary owner ID")
    private Long primaryOwnerId;
    private Long secondaryOwnerId;
    @NotNull(message = "Balance cannot be null")
    private BigDecimal balance;

    @DecimalMax(value = "1000.00", message = "The minimum balance has to be less than 1000")
    @DecimalMin(value = "100.00", message = "The minimum balance has to be more than 100")
    private BigDecimal creditLimit;
    @DecimalMax(value = "0.2", message = "The interest rate has to be less than 0.2")
    @DecimalMin(value = "0.1" , message = "The interest rate has to be more than 0.1")
    private BigDecimal interestRate;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate lastInterestUpdate;
    /** CONSTRUCTORS **/

    public CreditCardDTO(LocalDateTime creationTime, @NotNull Long primaryOwnerId, Long secondaryOwnerId, @NotNull BigDecimal balance, @DecimalMax(value = "1000.00", message = "The minimum balance has to be less than 1000") @DecimalMin(value = "100.00", message = "The minimum balance has to be more than 100") BigDecimal creditLimit, @DecimalMax(value = "0.5", message = "The interest rate has to be less than 0.5") @DecimalMin(value = "0", message = "The interest rate has to be more than 0") BigDecimal interestRate) {
        super(creationTime);
        setPrimaryOwnerId(primaryOwnerId);
        setSecondaryOwnerId(secondaryOwnerId);
        setBalance(balance);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
    }

    /** GETTERS & SETTERS **/

    public Long getPrimaryOwnerId() {return primaryOwnerId;}
    public void setPrimaryOwnerId(Long primaryOwnerId) {this.primaryOwnerId = primaryOwnerId;}
    public Long getSecondaryOwnerId() {return secondaryOwnerId;}
    public void setSecondaryOwnerId(Long secondaryOwnerId) {this.secondaryOwnerId = secondaryOwnerId;}
    public BigDecimal getBalance() {return balance;}
    public void setBalance(BigDecimal balance) {this.balance = balance;}
    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getInterestRate() {return interestRate;}
    public void setInterestRate(BigDecimal interestRate) {this.interestRate = interestRate;}

    public LocalDate getLastInterestUpdate() {
        return lastInterestUpdate;
    }

    public void setLastInterestUpdate(LocalDate lastInterestUpdate) {
        this.lastInterestUpdate = lastInterestUpdate;
    }
}
