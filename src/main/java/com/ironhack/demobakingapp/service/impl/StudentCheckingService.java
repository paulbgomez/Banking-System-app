package com.ironhack.demobakingapp.service.impl;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.classes.Time;
import com.ironhack.demobakingapp.controller.DTO.CheckingDTO;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.model.Accounts.Checking;
import com.ironhack.demobakingapp.model.Accounts.StudentChecking;
import com.ironhack.demobakingapp.repository.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.CheckingRepository;
import com.ironhack.demobakingapp.repository.StudentCheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentCheckingService {

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    public StudentChecking add(CheckingDTO checkingDTO) {

        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(checkingDTO.getPrimaryOwnerId());
        AccountHolder accountHolder1 = checkingDTO.getSecondaryOwnerId() != null ?
                accountHolderRepository.findById(checkingDTO.getSecondaryOwnerId()).get() :
                null;

        StudentChecking studentChecking = new StudentChecking();
        Checking checking = new Checking();

        if (accountHolder.isPresent() && (Time.years(accountHolder.get().getBirthDate()) < 25)) {
            studentChecking.setBalance(new Money(checkingDTO.getBalance()));
            studentChecking.setPrimaryOwner(accountHolder.get());
            studentChecking.setStatus(checkingDTO.getStatus());
            studentChecking.setSecretKey(checkingDTO.getSecretKey());
            if (accountHolder1 != null) {
                studentChecking.setSecondaryOwner(accountHolder1);
            }

            return studentCheckingRepository.save(studentChecking);

        } else {
            checking.setBalance(new Money(checkingDTO.getBalance()));
            checking.setPrimaryOwner(accountHolder.get());
            checking.setStatus(checkingDTO.getStatus());
            checking.setSecretKey(checkingDTO.getSecretKey());
            checking.getMonthlyFee();
            checking.getMinimumBalance();

            if (accountHolder1 != null) {checking.setSecondaryOwner(accountHolder1);}

            return checkingRepository.save(checking);
        }


    }

}
