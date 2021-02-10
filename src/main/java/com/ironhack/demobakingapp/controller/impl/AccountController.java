package com.ironhack.demobakingapp.controller.impl;

import com.ironhack.demobakingapp.controller.DTO.MovementDTO;
import com.ironhack.demobakingapp.model.Movement;
import com.ironhack.demobakingapp.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/new-transference/")
    @ResponseStatus(HttpStatus.CREATED)
    public Movement transfer(@RequestBody MovementDTO movementDTO, Principal principal){
        return accountService.transfer(movementDTO, principal.getName());
    }
}
