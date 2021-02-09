package com.ironhack.demobakingapp.controller.interfaces;

import com.ironhack.demobakingapp.controller.DTO.BalanceDTO;
import com.ironhack.demobakingapp.controller.DTO.SavingsDTO;
import com.ironhack.demobakingapp.model.Savings;
import com.ironhack.demobakingapp.security.CustomUserDetails;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.List;

public interface ISavingsController {

    public Savings add(SavingsDTO savingsDTO);
    public List<Savings> findAll();
    public BalanceDTO checkBalance(Long id, Principal username);


}
