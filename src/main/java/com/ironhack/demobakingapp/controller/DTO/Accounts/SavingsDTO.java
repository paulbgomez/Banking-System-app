package com.ironhack.demobakingapp.controller.DTO.Accounts;

import com.ironhack.demobakingapp.enums.Status;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SavingsDTO extends AccountDTO{

    /** PARAMS **/

    @NotNull(message = "You need at least one primary owner ID")
    private Long primaryOwnerId;
    private Long secondaryOwnerId;
    @NotNull(message = "Balance cannot be null")
    private BigDecimal balance;
    @NotNull(message = "Secretkey cannot be null")
    private String secretKey;
    @DecimalMax(value = "1000.00", message = "The minimum balance has to be less than 1000")
    @DecimalMin(value = "100.00", message = "The minimum balance has to be more than 100")
    private BigDecimal minimumBalance;
    @DecimalMax(value = "0.5", message = "The interest rate has to be less than 0.5")
    @DecimalMin(value = "0" , message = "The interest rate has to be more than 0")
    private BigDecimal interestRate;

    /** CONSTRUCTORS **/

    public SavingsDTO(LocalDateTime creationTime, @NotNull Long primaryOwnerId, Long secondaryOwnerId, @NotNull BigDecimal balance, @NotNull String secretKey, @DecimalMax(value = "1000.00", message = "The minimum balance has to be less than 1000") @DecimalMin(value = "100.00", message = "The minimum balance has to be more than 100") BigDecimal minimumBalance, @DecimalMax(value = "0.5", message = "The interest rate has to be less than 0.5") @DecimalMin(value = "0", message = "The interest rate has to be more than 0") BigDecimal interestRate) {
        super(creationTime);
        setPrimaryOwnerId(primaryOwnerId);
        setSecondaryOwnerId(secondaryOwnerId);
        setBalance(balance);
        setSecretKey(secretKey);
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
    public BigDecimal getMinimumBalance() {return minimumBalance;}
    public void setMinimumBalance(BigDecimal minimumBalance) {this.minimumBalance = minimumBalance;}
    public BigDecimal getInterestRate() {return interestRate;}
    public void setInterestRate(BigDecimal interestRate) {this.interestRate = interestRate;}

}
