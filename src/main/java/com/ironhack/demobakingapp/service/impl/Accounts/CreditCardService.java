package com.ironhack.demobakingapp.service.impl.Accounts;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.classes.Time;
import com.ironhack.demobakingapp.controller.DTO.Accounts.CreditCardDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.SavingsDTO;
import com.ironhack.demobakingapp.model.Accounts.CreditCard;
import com.ironhack.demobakingapp.model.Accounts.Savings;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.repository.Accounts.CreditCardRepository;
import com.ironhack.demobakingapp.repository.Users.AccountHolderRepository;
import com.ironhack.demobakingapp.service.interfaces.Accounts.ICreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class CreditCardService implements ICreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    Random random = new Random();

    /** Add and save a new Credit Card **/
    public CreditCard add(CreditCardDTO creditCardDTO){
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(creditCardDTO.getPrimaryOwnerId());
        AccountHolder accountHolder1 = creditCardDTO.getSecondaryOwnerId() != null ?
                accountHolderRepository.findById(creditCardDTO.getSecondaryOwnerId()).get() :
                null ;

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

    /** Save a new credit card **/
    public CreditCard save(CreditCard creditCard){
        return creditCardRepository.save(creditCard);
    }

    /** Find by id **/
    public CreditCard findById(Long id){
        return creditCardRepository.findById(id).get();
    }

    /** Add interest rate **/
    public void addInterestRate(Long id){
        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
        Integer month = Time.months(creditCard.get().getLastInterestUpdate());

        if (creditCard.isPresent() && month >= 1){
                BigDecimal calculatedInterest = creditCard.get().getBalance().getAmount()
                        .multiply(creditCard.get().getInterestRate().divide(new BigDecimal("12"), 2, RoundingMode.HALF_EVEN))
                        .multiply(new BigDecimal(Time.months(creditCard.get().getLastInterestUpdate())));
                creditCard.get().getBalance().increaseAmount(calculatedInterest);
                creditCard.get().setLastInterestUpdate(creditCard.get().getLastInterestUpdate().plusMonths(Time.months(creditCard.get().getLastInterestUpdate())));
        }
        creditCardRepository.save(creditCard.get());
    }

    /** Transform a DTO to model. For the tests **/
    public CreditCard transformToCreditCardFromDTO(CreditCardDTO creditCardDTO){
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(creditCardDTO.getPrimaryOwnerId());
        AccountHolder accountHolder1 = creditCardDTO.getSecondaryOwnerId() != null ?
                accountHolderRepository.findById(creditCardDTO.getSecondaryOwnerId()).get() :
                null ;

        CreditCard creditCard = new CreditCard();

        if (accountHolder.isPresent()){
            creditCard.setBalance(new Money(creditCardDTO.getBalance()));
            creditCard.setPrimaryOwner(accountHolder.get());
            creditCard.setInterestRate(creditCardDTO.getInterestRate() != null ?
                    creditCardDTO.getInterestRate() :
                    new BigDecimal(0.02));
            creditCard.setCreditLimit(creditCardDTO.getCreditLimit() != null ?
                    new Money(creditCardDTO.getCreditLimit()) :
                    new Money(new BigDecimal(random.nextInt(90000) + 101)));

            creditCard.setLastInterestUpdate(LocalDate.now());
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The account holder does not exist");
        }

        if (accountHolder1 != null) {creditCard.setSecondaryOwner(accountHolder1);}

        return creditCard;
    }
}
