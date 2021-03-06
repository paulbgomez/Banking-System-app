package com.ironhack.demobakingapp.service.impl.Users;

import com.ironhack.demobakingapp.controller.DTO.Users.AccountHolderDTO;
import com.ironhack.demobakingapp.enums.UserRole;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.model.Users.Role;
import com.ironhack.demobakingapp.repository.Users.AccountHolderRepository;
import com.ironhack.demobakingapp.service.interfaces.Users.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AccountHolderService implements IAccountHolderService {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    /** Add a new account holder **/
    public AccountHolder add(AccountHolderDTO accountHolderDTO){

        AccountHolder accountHolder = new AccountHolder(
                accountHolderDTO.getName(),
                accountHolderDTO.getPassword(),
                accountHolderDTO.getUsername(),
                accountHolderDTO.getBirthDate(),
                accountHolderDTO.getMailingAddressDTO(),
                accountHolderDTO.getMailingAddressDTO()
                );
        Role role = new Role(UserRole.ACCOUNT_HOLDER, accountHolder);
        Set<Role> roles = Stream.of(role).collect(Collectors.toCollection(HashSet::new));
        accountHolder.setRoles(roles);
        accountHolderRepository.save(accountHolder);
        return accountHolder;

    }

    /** Find an account holder by Id **/
    public AccountHolder findById(Long id){
        return accountHolderRepository.getOne(id);
    }

    /** Find an account holder by username **/
    public AccountHolder findByUsername(String username){
        return accountHolderRepository.findByUsername(username).get();
    }



}
