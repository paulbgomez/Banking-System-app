package com.ironhack.demobakingapp.controller.impl;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.controller.DTO.SavingsDTO;
import com.ironhack.demobakingapp.model.AccountHolder;
import com.ironhack.demobakingapp.model.Savings;
import com.ironhack.demobakingapp.repository.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.SavingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SavingsController {

    @Autowired
    SavingsRepository savingsRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;

    @PostMapping("/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings create(@RequestBody @Valid SavingsDTO savingsDTO){

        AccountHolder accountHolder = accountHolderRepository.findById(savingsDTO.getPrimaryOwnerId()).get();

        Savings savings = new Savings(new Money(savingsDTO.getBalance()), accountHolder, savingsDTO.getStatus(), savingsDTO.getSecretKey());

        return savingsRepository.save(savings);
    }

}
