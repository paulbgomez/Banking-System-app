package com.ironhack.demobakingapp.model;

import com.ironhack.demobakingapp.enums.UserRole;

import javax.persistence.*;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UserRole userRole;

    @ManyToOne //fetch?? de algun tipo?
    private User user;
}
