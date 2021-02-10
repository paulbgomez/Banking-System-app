package com.ironhack.demobakingapp.controller.DTO.Accounts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ironhack.demobakingapp.enums.Status;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CheckingDTO {

    /** PARAMS **/

    @NotNull(message = "You need at least one primary owner ID")
    private Long primaryOwnerId;
    private Long secondaryOwnerId;
    @NotNull(message = "Balance cannot be null")
    @NotEmpty
    private BigDecimal balance;
    @NotNull(message = "Secretkey cannot be null")
    private String secretKey;
    @NotNull(message = "Status cannot be null")
    private Status status;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDateTime lastFee;

    /** CONSTRUCTORS **/

    public CheckingDTO(@NotNull Long primaryOwnerId, Long secondaryOwnerId, @NotNull BigDecimal balance, @NotNull String secretKey, @NotNull Status status) {
        setPrimaryOwnerId(primaryOwnerId);
        setSecondaryOwnerId(secondaryOwnerId);
        setBalance(balance);
        setSecretKey(secretKey);
        setStatus(status);
        setLastFee(LocalDateTime.now());
    }

    /** GETTERS & SETTERS **/

    public Long getPrimaryOwnerId() {
        return primaryOwnerId;
    }

    public void setPrimaryOwnerId(Long primaryOwnerId) {
        this.primaryOwnerId = primaryOwnerId;
    }

    public LocalDateTime getLastFee() {
        return lastFee;
    }

    public void setLastFee(LocalDateTime lastFee) {
        this.lastFee = lastFee;
    }

    public Long getSecondaryOwnerId() {
        return secondaryOwnerId;
    }

    public void setSecondaryOwnerId(Long secondaryOwnerId) {
        this.secondaryOwnerId = secondaryOwnerId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.secretKey = passwordEncoder.encode(secretKey);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
