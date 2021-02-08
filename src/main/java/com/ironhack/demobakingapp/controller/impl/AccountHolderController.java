package com.ironhack.demobakingapp.controller.impl;

import com.ironhack.demobakingapp.controller.DTO.AccountHolderDTO;
import com.ironhack.demobakingapp.model.AccountHolder;
import com.ironhack.demobakingapp.service.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountHolderController {

    @Autowired
    private IAccountHolderService accountHolderService;

    @PostMapping("/accountholder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder create(AccountHolderDTO accountHolderDTO){
        return accountHolderService.create(accountHolderDTO);
    }


}
