package com.ironhack.demobakingapp.service.interfaces.Users;

import com.ironhack.demobakingapp.controller.DTO.Transferences.BalanceDTO;

public interface IAdminService {
    BalanceDTO checkBalance(Long id);
    void modify(Long id);



}
