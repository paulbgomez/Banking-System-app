package com.ironhack.demobakingapp.controller.DTO.Accounts;

import com.ironhack.demobakingapp.enums.Status;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StudentCheckingDTO extends AccountDTO{

    /** PARAMS **/

    @NotNull(message = "You need at least one primary owner ID")
    private Long primaryOwnerId;
    private Long secondaryOwnerId;
    @NotNull(message = "Balance cannot be null")
    private BigDecimal balance;
    @NotNull(message = "Secret key cannot be null")
    private String secretKey;
    @NotNull(message = "Status cannot be null")
    private Status status;

    /** CONSTRUCTOR **/

    public StudentCheckingDTO(LocalDateTime creationTime, @NotNull Long primaryOwnerId, Long secondaryOwnerId, @NotNull BigDecimal balance, @NotNull String secretKey) {
        super(creationTime);
        setPrimaryOwnerId(primaryOwnerId);
        setSecondaryOwnerId(secondaryOwnerId);
        setBalance(balance);
        setSecretKey(secretKey);
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
        this.secretKey = secretKey;
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.secretKey = passwordEncoder.encode(secretKey);
    }


}
