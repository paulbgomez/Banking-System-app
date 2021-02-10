package com.ironhack.demobakingapp.controller.DTO;

import com.ironhack.demobakingapp.enums.Status;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SavingsDTO {
    /** PARAMS **/

    @NotNull(message = "You need at least one primary owner ID")
    private Long primaryOwnerId;
    private Long secondaryOwnerId;
    @NotNull(message = "Balance cannot be null")
    private BigDecimal balance;
    @NotNull(message = "Secretkey cannot be null")
    private String secretKey;
    @NotNull(message = "Status cannot be null")
    private Status status;
    @DecimalMax(value = "1000.00", message = "The minimum balance has to be less than 1000")
    @DecimalMin(value = "100.00", message = "The minimum balance has to be more than 100")
    private BigDecimal minimumBalance;
    @DecimalMax(value = "0.5", message = "The interest rate has to be less than 0.5")
    @DecimalMin(value = "0" , message = "The interest rate has to be more than 0")
    private BigDecimal interestRate;

    /** CONSTRUCTORS **/

    public SavingsDTO(@NotNull Long primaryOwnerId, Long secondaryOwnerId, @NotNull BigDecimal balance, @NotNull String secretKey, @NotNull Status status, @DecimalMax(value = "1000.00", message = "The minimum balance has to be less than 1000") @DecimalMin(value = "100.00", message = "The minimum balance has to be more than 100") BigDecimal minimumBalance, @DecimalMax(value = "0.5", message = "The interest rate has to be less than 0.5") @DecimalMin(value = "0", message = "The interest rate has to be more than 0") BigDecimal interestRate) {
        setPrimaryOwnerId(primaryOwnerId);
        setSecondaryOwnerId(secondaryOwnerId);
        setBalance(balance);
        setSecretKey(secretKey);
        setStatus(status);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
    }

    /** GETTERS & SETTERS **/

    public Long getPrimaryOwnerId() {return primaryOwnerId;}
    public void setPrimaryOwnerId(Long primaryOwnerId) {this.primaryOwnerId = primaryOwnerId;}
    public Long getSecondaryOwnerId() {return secondaryOwnerId;}
    public void setSecondaryOwnerId(Long secondaryOwnerId) {this.secondaryOwnerId = secondaryOwnerId;}
    public BigDecimal getBalance() {return balance;}
    public void setBalance(BigDecimal balance) {this.balance = balance;}
    public String getSecretKey() {return secretKey;}
    public void setSecretKey(String secretKey) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.secretKey = passwordEncoder.encode(secretKey);
    }
    public Status getStatus() {return status;}
    public void setStatus(Status status) {this.status = status;}
    public BigDecimal getMinimumBalance() {return minimumBalance;}
    public void setMinimumBalance(BigDecimal minimumBalance) {this.minimumBalance = minimumBalance;}
    public BigDecimal getInterestRate() {return interestRate;}
    public void setInterestRate(BigDecimal interestRate) {this.interestRate = interestRate;}

}
