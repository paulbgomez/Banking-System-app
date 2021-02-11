package com.ironhack.demobakingapp.model.Accounts;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.enums.Status;
import com.ironhack.demobakingapp.model.Users.AccountHolder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class StudentChecking extends Account{

    /** PARAMS **/

    @Enumerated(EnumType.STRING)
    protected Status status;
    protected String secretKey;

    /** CONSTRUCTORS **/

    public StudentChecking() {
    }

    public StudentChecking(Status status, String secretKey) {
        this.status = status;
        this.secretKey = secretKey;
    }

    public StudentChecking(Money balance, AccountHolder primaryOwner, Status status, String secretKey) {
        super(balance, primaryOwner);
        this.status = status;
        this.secretKey = secretKey;
    }

    /** GETTERS & SETTERS **/

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

