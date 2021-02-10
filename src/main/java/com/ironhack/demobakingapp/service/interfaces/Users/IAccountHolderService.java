package com.ironhack.demobakingapp.service.interfaces.Users;


import com.ironhack.demobakingapp.controller.DTO.Accounts.AccountHolderDTO;
import com.ironhack.demobakingapp.model.Users.AccountHolder;

public interface IAccountHolderService {

        public AccountHolder add(AccountHolderDTO accountHolderDTO);
        public AccountHolder findById(Long id);

}
