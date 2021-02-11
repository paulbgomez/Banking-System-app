package com.ironhack.demobakingapp.controller.interfaces;

import com.ironhack.demobakingapp.controller.DTO.Transferences.BalanceDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

public interface ISavingsController {

     BalanceDTO checkBalance(Long id, Principal principal);

    }
