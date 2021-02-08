package com.ironhack.demobakingapp.service.impl;

import com.ironhack.demobakingapp.controller.DTO.AccountHolderDTO;
import com.ironhack.demobakingapp.enums.UserRole;
import com.ironhack.demobakingapp.model.AccountHolder;
import com.ironhack.demobakingapp.model.Role;
import com.ironhack.demobakingapp.repository.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AccountHolderService {

    @Autowired
    private AccountHolderRepository accountHolderRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AccountHolder create(AccountHolderDTO accountHolderDTO){
        String encodedPassword = passwordEncoder.encode(accountHolderDTO.getPassword());

        AccountHolder accountHolder = new AccountHolder(
                accountHolderDTO.getName(),
                encodedPassword,
                accountHolderDTO.getUsername(),
                accountHolderDTO.getBirthDate(),
                accountHolderDTO.getMailingAddress(),
                accountHolderDTO.getPrimaryAddress()
                );

        accountHolder.addRole(new Role(UserRole.ACCOUNT_HOLDER, accountHolder));

        return accountHolder;
    }

}
