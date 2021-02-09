package com.ironhack.demobakingapp.controller.impl;

import com.ironhack.demobakingapp.controller.DTO.AccountHolderDTO;
import com.ironhack.demobakingapp.controller.interfaces.IAccountHolderController;
import com.ironhack.demobakingapp.model.AccountHolder;
import com.ironhack.demobakingapp.security.CustomUserDetails;
import com.ironhack.demobakingapp.service.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountHolderController implements IAccountHolderController {

    @Autowired
    IAccountHolderService accountHolderService;

    @PostMapping("/accountholder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder create(@RequestBody AccountHolderDTO accountHolderDTO){
        return accountHolderService.create(accountHolderDTO);
    }

    //create User
    //saving account POST check primaryOwner Id
    //getBalance


}
