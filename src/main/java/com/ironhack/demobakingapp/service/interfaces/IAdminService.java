package com.ironhack.demobakingapp.service.interfaces;

import com.ironhack.demobakingapp.controller.DTO.BalanceDTO;
import com.ironhack.demobakingapp.model.Account;
import org.springframework.security.core.Authentication;

public interface IAdminService {
    public BalanceDTO checkBalance(Long id);
    public void modify(Long id);



}
