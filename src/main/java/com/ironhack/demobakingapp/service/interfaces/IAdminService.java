package com.ironhack.demobakingapp.service.interfaces;

import com.ironhack.demobakingapp.controller.DTO.BalanceDTO;

public interface IAdminService {
    public BalanceDTO checkBalance(Long id);
    public void modify(Long id);



}
