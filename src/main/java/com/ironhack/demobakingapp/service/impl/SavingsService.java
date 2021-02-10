package com.ironhack.demobakingapp.service.impl;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.controller.DTO.BalanceDTO;
import com.ironhack.demobakingapp.controller.DTO.SavingsDTO;
import com.ironhack.demobakingapp.enums.UserRole;
import com.ironhack.demobakingapp.model.AccountHolder;
import com.ironhack.demobakingapp.model.Admin;
import com.ironhack.demobakingapp.model.Savings;
import com.ironhack.demobakingapp.model.User;
import com.ironhack.demobakingapp.repository.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.AdminRepository;
import com.ironhack.demobakingapp.repository.SavingsRepository;
import com.ironhack.demobakingapp.repository.UserRepository;
import com.ironhack.demobakingapp.security.CustomUserDetails;
import com.ironhack.demobakingapp.service.interfaces.ISavingsService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class SavingsService implements ISavingsService {

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;


    public Savings add(SavingsDTO savingsDTO){
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(savingsDTO.getPrimaryOwnerId());
        AccountHolder accountHolder1 = savingsDTO.getSecondaryOwnerId() != null ?
                accountHolderRepository.findById(savingsDTO.getSecondaryOwnerId()).get() :
                null ;

        Random random = new Random();

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
                    new Money(new BigDecimal(random.nextInt(900) + 101)));

            savings.setLastFee(LocalDateTime.now());
        } else {
            throw new IllegalArgumentException("The account holder does not exist");
        }

        if (accountHolder1 != null) {savings.setSecondaryOwner(accountHolder1);}

        return savingsRepository.save(savings);
    }



    public List<Savings> findAll(){ return savingsRepository.findAll();}
    public Savings findById(Long id){return savingsRepository.findById(id).get();}


    public Savings transformToSavingsFromDTO(SavingsDTO savingsDTO){
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

        return savings;
    }

    public static Integer years(LocalDateTime date){
        Integer quantityYears = Period.between(date.toLocalDate(), LocalDate.now()).getYears();
        return quantityYears;
    }

    public void addInterestRate(Long id){
        Optional<Savings> savings = savingsRepository.findById(id);
        Integer year = Period.between(savings.get().getLastFee().toLocalDate(), LocalDate.now()).getYears();

        if (savings.isPresent() && year >= 1){
            BigDecimal newInterestRate = savings.get().getBalance().getAmount()
                    .multiply(savings.get().getInterestRate())
                    .multiply(new BigDecimal(years(savings.get().getLastFee())));
            savings.get().getBalance().increaseAmount(newInterestRate);
            savings.get().setLastFee(LocalDateTime.now());
        }
    }

    public BalanceDTO checkBalance(Long id, String username){

        User user = userRepository.findByUsername(username).get();

        AccountHolder accountHolder = accountHolderRepository.findByUsername(user.getUsername()).get();
        Savings savings = savingsRepository.findById(id).get();
        BalanceDTO balance = new BalanceDTO(savings.getId(), savings.getBalance().getAmount(), savings.getBalance().getCurrency());

        if(accountHolder.showAccounts().contains(savings)){
            addInterestRate(id);
            return balance;
        } else if (savings.isBelowMinimumBalance()) {
            savings.getBalance().decreaseAmount(savings.getPenalty());
            return balance;
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not admin nor have saving accounts");
        }
    }

    public BalanceDTO checkBalanceAdmin(Long id, String username){

        User user = userRepository.findByUsername(username).get();

        Admin admin = adminRepository.findByUsername(user.getUsername());
        Savings savings = savingsRepository.findById(id).get();
        BalanceDTO balance = new BalanceDTO(savings.getId(), savings.getBalance().getAmount(), savings.getBalance().getCurrency());

        if(admin.getUsername().equals(username)){
            addInterestRate(id);
            return balance;
        } else if (savings.isBelowMinimumBalance()) {
            savings.getBalance().decreaseAmount(savings.getPenalty());
            return balance;
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not admin nor have saving accounts");
        }
    }
}
