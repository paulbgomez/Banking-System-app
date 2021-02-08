package com.ironhack.demobakingapp.controller.interfaces;

import com.ironhack.demobakingapp.controller.DTO.AccountHolderDTO;
import com.ironhack.demobakingapp.model.AccountHolder;

public interface IAccountHolderController {

    public AccountHolder create(AccountHolderDTO accountHolderDTO);
}
