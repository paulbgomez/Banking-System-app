package com.ironhack.demobakingapp.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="id")
public class Admin extends User{

    public Admin() {
    }

    public Admin(String name, String password, String userName) {
        super(name, password, userName);
    }
}
