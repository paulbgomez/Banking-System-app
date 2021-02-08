package com.ironhack.demobakingapp.model;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.enums.Status;

import javax.persistence.*;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class StudentChecking extends Account{

    protected Status status;
    protected String secretKey;

    public StudentChecking() {
    }

    public StudentChecking(Status status, String secretKey) {
        this.status = status;
        this.secretKey = secretKey;
    }

    public StudentChecking(Money balanceAmount, Money penaltyAmount, AccountHolder primaryOwner, AccountHolder secondaryOwner, List<Transference> sentTransference, List<Transference> receivedTransference, Status status, String secretKey) {
        super(balanceAmount, penaltyAmount, primaryOwner, secondaryOwner, sentTransference, receivedTransference);
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
