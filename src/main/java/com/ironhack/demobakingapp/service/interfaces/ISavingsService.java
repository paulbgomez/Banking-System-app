package com.ironhack.demobakingapp.service.interfaces;

import com.ironhack.demobakingapp.controller.DTO.BalanceDTO;
import com.ironhack.demobakingapp.controller.DTO.SavingsDTO;
import com.ironhack.demobakingapp.model.Accounts.Savings;

import java.util.List;

public interface ISavingsService {

    public Savings add(SavingsDTO savingsDTO);
    public List<Savings> findAll();
    public Savings findById(Long id);
    public BalanceDTO checkBalance(Long id, String username);
    public Savings transformToSavingsFromDTO(SavingsDTO savingsDTO);
    public void addInterestRate(Long id);



    }
