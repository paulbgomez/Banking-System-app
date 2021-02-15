package com.ironhack.demobakingapp.controller.interfaces;

import com.ironhack.demobakingapp.controller.DTO.Transferences.BalanceDTO;

import java.security.Principal;

public interface ISavingsController {

    /** GET REQUEST **/
     BalanceDTO checkBalance(Long id, Principal principal);
    }
