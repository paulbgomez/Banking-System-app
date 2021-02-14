package com.ironhack.demobakingapp.model.Users;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="id")
public class Admin extends User{

    /** CONSTRUCTORS **/

    public Admin() {
    }

    public Admin(String name, String password, String userName) {
        super(name, password, userName);
    }
}
