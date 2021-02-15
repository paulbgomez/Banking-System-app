package com.ironhack.demobakingapp.controller.impl;

import com.ironhack.demobakingapp.controller.DTO.Transferences.BalanceDTO;
import com.ironhack.demobakingapp.controller.interfaces.ISavingsController;
import com.ironhack.demobakingapp.service.interfaces.Accounts.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
public class SavingsController implements ISavingsController {

    @Autowired
    private IAccountService accountService;

    /** GET REQUEST **/

    /** Show One Savings Account Balance For Account Holder **/
    @GetMapping("/savings/check-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BalanceDTO checkBalance(@PathVariable Long id, Principal principal){
        return accountService.checkBalance(id, principal.getName());
    }

}
