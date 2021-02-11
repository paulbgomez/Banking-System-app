package com.ironhack.demobakingapp.service.interfaces.Accounts;

import com.ironhack.demobakingapp.controller.DTO.Accounts.SavingsDTO;
import com.ironhack.demobakingapp.controller.DTO.Transferences.BalanceDTO;
import com.ironhack.demobakingapp.model.Accounts.Savings;

import java.util.List;

public interface ISavingsService {

    Savings add(SavingsDTO savingsDTO);
    List<Savings> findAll();
    Savings findById(Long id);
    BalanceDTO checkBalance(Long id, String username);
    Savings transformToSavingsFromDTO(SavingsDTO savingsDTO);
    void addInterestRate(Long id);
    BalanceDTO checkBalanceAdmin(Long id, String username);

}
