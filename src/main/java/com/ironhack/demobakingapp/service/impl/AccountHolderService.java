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

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Role role = new Role(UserRole.ACCOUNT_HOLDER, accountHolder);
        Set<Role> roles = Stream.of(role).collect(Collectors.toCollection(HashSet::new));
        accountHolder.setRoles(roles);
        accountHolderRepository.save(accountHolder);
        return accountHolder;

    }

}
