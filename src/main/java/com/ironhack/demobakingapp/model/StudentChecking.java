package com.ironhack.demobakingapp.model;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.enums.Status;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class StudentChecking extends Account{

    @Enumerated(EnumType.STRING)
    protected Status status;
    protected String secretKey;

    public StudentChecking() {
    }

    public StudentChecking(Status status, String secretKey) {
        this.status = status;
        this.secretKey = secretKey;
    }

    public StudentChecking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Status status, String secretKey) {
        super(balance, primaryOwner, secondaryOwner);
        this.status = status;
        this.secretKey = secretKey;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
