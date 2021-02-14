package com.ironhack.demobakingapp.service.interfaces.Users;


import com.ironhack.demobakingapp.controller.DTO.Users.AccountHolderDTO;
import com.ironhack.demobakingapp.model.Users.AccountHolder;

public interface IAccountHolderService {

        AccountHolder add(AccountHolderDTO accountHolderDTO);
        AccountHolder findById(Long id);
        AccountHolder findByUsername(String username);

}
