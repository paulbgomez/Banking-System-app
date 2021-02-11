package com.ironhack.demobakingapp.service.impl.Accounts;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.classes.Time;
import com.ironhack.demobakingapp.controller.DTO.Accounts.CreditCardDTO;
import com.ironhack.demobakingapp.model.Accounts.CreditCard;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.repository.Accounts.CreditCardRepository;
import com.ironhack.demobakingapp.repository.Users.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    public CreditCard add(CreditCardDTO creditCardDTO){
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(creditCardDTO.getPrimaryOwnerId());
        AccountHolder accountHolder1 = creditCardDTO.getSecondaryOwnerId() != null ?
                accountHolderRepository.findById(creditCardDTO.getSecondaryOwnerId()).get() :
                null ;

        Random random = new Random();

        CreditCard creditCard = new CreditCard();

        if (accountHolder.isPresent()){
            creditCard.setBalance(new Money(creditCardDTO.getBalance()));
            creditCard.setPrimaryOwner(accountHolder.get());
            creditCard.setLastInterestUpdate(LocalDate.now());
            creditCard.setCreationTime(LocalDateTime.now());
            creditCard.setInterestRate(creditCardDTO.getInterestRate() != null ?
                    creditCardDTO.getInterestRate() :
                    new BigDecimal(0.02));
            creditCard.setCreditLimit(creditCardDTO.getCreditLimit() != null ?
                    new Money(creditCardDTO.getCreditLimit()) :
                    new Money(new BigDecimal(random.nextInt(900) + 101)));

        } else {
            throw new IllegalArgumentException("The account holder does not exist");
        }

        if (accountHolder1 != null) {creditCard.setSecondaryOwner(accountHolder1);}

        return creditCardRepository.save(creditCard);
    }

    public CreditCard save(CreditCard creditCard){
        return creditCardRepository.save(creditCard);
    }

    public CreditCard findById(Long id){
        return creditCardRepository.findById(id).get();
    }

    public void addInterestRate(Long id){
        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
        System.out.println(creditCard.get().getLastInterestUpdate());
        Integer month = Time.months(creditCard.get().getLastInterestUpdate());
        System.out.println(month);

        if (creditCard.isPresent() && month >= 1){
                BigDecimal calculatedInterest = creditCard.get().getBalance().getAmount()
                        .multiply(creditCard.get().getInterestRate().divide(new BigDecimal("12"), 2, RoundingMode.HALF_EVEN))
                        .multiply(new BigDecimal(Time.months(creditCard.get().getLastInterestUpdate())));
                creditCard.get().getBalance().increaseAmount(calculatedInterest);
                creditCard.get().setLastInterestUpdate(creditCard.get().getLastInterestUpdate().plusMonths(Time.months(creditCard.get().getLastInterestUpdate())));
        }
    }
}