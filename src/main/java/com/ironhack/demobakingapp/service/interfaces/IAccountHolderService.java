package com.ironhack.demobakingapp.service.interfaces;


import com.ironhack.demobakingapp.controller.DTO.AccountHolderDTO;
import com.ironhack.demobakingapp.model.AccountHolder;

public interface IAccountHolderService {

        public AccountHolder create(AccountHolderDTO accountHolderDTO);

}
