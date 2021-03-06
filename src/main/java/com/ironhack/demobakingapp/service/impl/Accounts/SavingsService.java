package com.ironhack.demobakingapp.service.impl.Accounts;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.classes.Time;
import com.ironhack.demobakingapp.controller.DTO.Accounts.SavingsDTO;
import com.ironhack.demobakingapp.controller.DTO.Transferences.BalanceDTO;
import com.ironhack.demobakingapp.model.Accounts.Savings;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.model.Users.Admin;
import com.ironhack.demobakingapp.model.Users.User;
import com.ironhack.demobakingapp.repository.Accounts.SavingsRepository;
import com.ironhack.demobakingapp.repository.Users.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.Users.AdminRepository;
import com.ironhack.demobakingapp.repository.Users.UserRepository;
import com.ironhack.demobakingapp.service.interfaces.Accounts.ISavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    Random random = new Random();

    /** Add a new savings account **/
    public Savings add(SavingsDTO savingsDTO){
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(savingsDTO.getPrimaryOwnerId());
        AccountHolder accountHolder1 = savingsDTO.getSecondaryOwnerId() != null ?
                accountHolderRepository.findById(savingsDTO.getSecondaryOwnerId()).get() :
                null ;
        Savings savings = new Savings();
        if(accountHolder.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is null");
        }
        savings.setBalance(new Money(savingsDTO.getBalance()));
        savings.setPrimaryOwner(accountHolder.get());
        savings.setStatus(savingsDTO.getStatus());
        savings.setSecretKey(savingsDTO.getSecretKey());
        savings.setCreationTime(LocalDateTime.now());
        savings.setInterestRate(savingsDTO.getInterestRate() != null ?
                savingsDTO.getInterestRate() :
                new BigDecimal("0.0025"));
        savings.setMinimumBalance(savingsDTO.getMinimumBalance() != null ?
                new Money(savingsDTO.getMinimumBalance()) :
                new Money(new BigDecimal(random.nextInt(900) + 101)));
        savings.setLastFee(LocalDateTime.now());

        if (accountHolder1 != null) {savings.setSecondaryOwner(accountHolder1);}

        return savingsRepository.save(savings);
    }

    /** Return all savings accounts **/
    public List<Savings> findAll(){ return savingsRepository.findAll();}

    /** Find a savings account by Id **/
    public Savings findById(Long id){return savingsRepository.findById(id).get();}

    /** Save a saving account **/
    public Savings save(Savings savings){return savingsRepository.save(savings);}

    /** Add interest rate **/
    public void addInterestRate(Long id){
        Optional<Savings> savings = savingsRepository.findById(id);
        Integer year = Time.years(savings.get().getLastFee().toLocalDate());

        if (year >= 1){
            BigDecimal newInterestRate = savings.get().getBalance().getAmount()
                    .multiply(savings.get().getInterestRate())
                    .multiply(new BigDecimal(Time.years(savings.get().getLastFee().toLocalDate())));
            savings.get().getBalance().increaseAmount(newInterestRate);
            savings.get().setLastFee(LocalDateTime.now());
        }
        savingsRepository.save(savings.get());
    }

    /** Check balance for an admin role **/
    public BalanceDTO checkBalanceAdmin(Long id, String username){

    Admin admin = adminRepository.findByUsername(username);
    Savings savings = savingsRepository.findById(id).get();
    BalanceDTO balance = new BalanceDTO(savings.getId(), savings.getBalance().getAmount(), savings.getBalance().getCurrency());
    if(admin == null){
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is null");
    }
    if(admin.getUsername().equals(username)){
        addInterestRate(id);
        return balance;
    } else if (savings.isBelowMinimumBalance()) {
        savings.getBalance().decreaseAmount(savings.getPenalty());
        return balance;
    } else {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User id does not match Admin permissions");
    }

    }

    /** Transform a DTO to model. For the tests **/
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
                    new BigDecimal("0.0025"));
            savings.setMinimumBalance(savingsDTO.getMinimumBalance() != null ?
                    new Money(savingsDTO.getMinimumBalance()) :
                    new Money(new BigDecimal(random.nextInt(900) + 101)));

            savings.setLastFee(LocalDateTime.now());
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The account holder does not exist");
        }

        if (accountHolder1 != null) {savings.setSecondaryOwner(accountHolder1);}

        return savings;
    }

}
