package com.ironhack.demobakingapp.service.impl;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.controller.DTO.CreditCardDTO;
import com.ironhack.demobakingapp.controller.DTO.SavingsDTO;
import com.ironhack.demobakingapp.model.AccountHolder;
import com.ironhack.demobakingapp.model.CreditCard;
import com.ironhack.demobakingapp.model.Savings;
import com.ironhack.demobakingapp.repository.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

//    public void addInterestRate(Long id){
//        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
//        Integer month = Period.between(creditCard.get().getLastFee().toLocalDate(), LocalDate.now()).getYears();
//
//        if (savings.isPresent() && year >= 1){
//            BigDecimal newInterestRate = savings.get().getBalance().getAmount()
//                    .multiply(savings.get().getInterestRate())
//                    .multiply(new BigDecimal(years(savings.get().getLastFee())));
//            savings.get().getBalance().increaseAmount(newInterestRate);
//            savings.get().setLastFee(LocalDateTime.now());
//        }
//    }
}
