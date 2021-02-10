package com.ironhack.demobakingapp.service.interfaces.Users;

import com.ironhack.demobakingapp.controller.DTO.Transferences.BalanceDTO;

public interface IAdminService {
    public BalanceDTO checkBalance(Long id);
    public void modify(Long id);



}
