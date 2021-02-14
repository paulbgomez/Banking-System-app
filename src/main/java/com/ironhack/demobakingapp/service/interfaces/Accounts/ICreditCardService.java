package com.ironhack.demobakingapp.service.interfaces.Accounts;
import com.ironhack.demobakingapp.controller.DTO.Accounts.CreditCardDTO;
import com.ironhack.demobakingapp.model.Accounts.CreditCard;

public interface ICreditCardService {
    CreditCard add(CreditCardDTO creditCardDTO);

    CreditCard save(CreditCard creditCard);

    CreditCard findById(Long id);

    void addInterestRate(Long id);

    CreditCard transformToCreditCardFromDTO(CreditCardDTO creditCardDTO);

}
