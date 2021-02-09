package com.ironhack.demobakingapp.service.impl;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.controller.DTO.SavingsDTO;
import com.ironhack.demobakingapp.model.AccountHolder;
import com.ironhack.demobakingapp.model.Savings;
import com.ironhack.demobakingapp.repository.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.SavingsRepository;
import com.ironhack.demobakingapp.service.interfaces.ISavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SavingsService implements ISavingsService {

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;


    public Savings add(SavingsDTO savingsDTO){
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(savingsDTO.getPrimaryOwnerId());
        AccountHolder accountHolder1 = savingsDTO.getSecondaryOwnerId() != null ?
                accountHolderRepository.findById(savingsDTO.getSecondaryOwnerId()).get() :
                null ;

        Savings savings = new Savings();

        if (accountHolder.isPresent()){
            savings.setBalance(new Money(savingsDTO.getBalance()));
            savings.setPrimaryOwner(accountHolder.get());
            savings.setStatus(savingsDTO.getStatus());
            savings.setSecretKey(savingsDTO.getSecretKey());
            savings.setInterestRate(savingsDTO.getInterestRate() != null ?
                    savingsDTO.getInterestRate() :
                    new BigDecimal(0.0025));
            savings.setMinimumBalance(savingsDTO.getMinimumBalance() != null ?
                    new Money(savingsDTO.getMinimumBalance()) :
                    new Money(new BigDecimal(Math.random()*1000)));

            savings.setLastFee(LocalDateTime.now());
        } else {
            throw new IllegalArgumentException("The account holder does not exist");
        }

        if (accountHolder1 != null) {savings.setSecondaryOwner(accountHolder1);}

        return savingsRepository.save(savings);
    }



    public List<Savings> findAll(){ return savingsRepository.findAll();}
    public Savings findById(Long id){return savingsRepository.findById(id).get();}

}
