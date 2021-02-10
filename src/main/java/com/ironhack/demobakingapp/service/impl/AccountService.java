package com.ironhack.demobakingapp.service.impl;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.model.*;
import com.ironhack.demobakingapp.repository.*;
import com.ironhack.demobakingapp.service.interfaces.ISavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.security.Principal;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private SavingsService savingsService;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private CheckingService checkingService;

    @Autowired
    private UserRepository userRepository;

    public void incrementBalance(Long id, BigDecimal amount, String username){

        Account typeOfAccount = accountRepository.findById(id).get();

        User user = userRepository.findByUsername(username).get();

        Admin admin = adminRepository.findByUsername(user.getUsername());

        if (admin.getUsername().equals(username)) {
            if (typeOfAccount.getClass().equals(Savings.class)){
                typeOfAccount.setBalance(new Money(typeOfAccount.getBalance().increaseAmount(amount)));
                savingsService.addInterestRate(id);
            } else if (typeOfAccount.getClass().equals(CreditCard.class)){
                typeOfAccount.setBalance(new Money(typeOfAccount.getBalance().increaseAmount(amount)));
                creditCardService.addInterestRate(id);
            } else {
                typeOfAccount.setBalance(new Money(typeOfAccount.getBalance().increaseAmount(amount)));
            }
        }
    }

    public void decrementBalance(Long id, BigDecimal amount, String username){

        Account typeOfAccount = accountRepository.findById(id).get();

        User user = userRepository.findByUsername(username).get();

        Admin admin = adminRepository.findByUsername(user.getUsername());

        if (admin.getUsername().equals(username)) {
            if (typeOfAccount.getClass().equals(Savings.class)){
                typeOfAccount.setBalance(new Money(typeOfAccount.getBalance().decreaseAmount(amount)));
                savingsService.addInterestRate(id);
            } else if (typeOfAccount.getClass().equals(CreditCard.class)){
                typeOfAccount.setBalance(new Money(typeOfAccount.getBalance().decreaseAmount(amount)));
                creditCardService.addInterestRate(id);
            } else {
                typeOfAccount.setBalance(new Money(typeOfAccount.getBalance().decreaseAmount(amount)));
            }
        }
    }



}
