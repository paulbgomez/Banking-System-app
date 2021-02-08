package com.ironhack.demobakingapp.service.impl;

import com.ironhack.demobakingapp.controller.DTO.AccountHolderDTO;
import com.ironhack.demobakingapp.enums.UserRole;
import com.ironhack.demobakingapp.model.AccountHolder;
import com.ironhack.demobakingapp.model.Role;
import com.ironhack.demobakingapp.repository.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.RoleRepository;
import com.ironhack.demobakingapp.service.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountHolderService implements IAccountHolderService {

    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private RoleRepository roleRepository;


    public AccountHolder create(AccountHolderDTO accountHolderDTO){

        AccountHolder accountHolder = new AccountHolder(
                accountHolderDTO.getName(),
                accountHolderDTO.getPassword(),
                accountHolderDTO.getUsername(),
                accountHolderDTO.getBirthDate(),
                accountHolderDTO.getMailingAddress(),
                accountHolderDTO.getPrimaryAddress()
                );
        Role role = roleRepository.save(new Role(UserRole.ACCOUNT_HOLDER, accountHolder));
        accountHolder.addRole(role);
        accountHolderRepository.save(accountHolder);
        return accountHolder;


    }

}
