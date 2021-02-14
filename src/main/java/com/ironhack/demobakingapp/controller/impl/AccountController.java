package com.ironhack.demobakingapp.controller.impl;

import com.ironhack.demobakingapp.controller.DTO.Transferences.MovementDTO;
import com.ironhack.demobakingapp.controller.interfaces.IAccountController;
import com.ironhack.demobakingapp.model.Movement;
import com.ironhack.demobakingapp.service.impl.Accounts.AccountService;
import com.ironhack.demobakingapp.service.interfaces.Accounts.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class AccountController implements IAccountController {

    @Autowired
    private IAccountService accountService;

    /** POST REQUEST **/

    /** New Transference Between Account Holders **/
    @PostMapping("/new-transference/")
    @ResponseStatus(HttpStatus.CREATED)
    public Movement transfer(@RequestBody @Valid MovementDTO movementDTO, Principal principal){
        return accountService.transfer(movementDTO, principal.getName());
    }

    /** New Transference To Third Party **/
    @PostMapping("/new-transference/to/{name}/{hashKey}")
    @ResponseStatus(HttpStatus.CREATED)
    public Movement transfer(@PathVariable String name,@PathVariable String hashKey, @RequestBody @Valid MovementDTO movementDTO, Principal principal){
        return accountService.transferToThirdParty(name, hashKey, movementDTO, principal.getName());
    }

    /** New Transference From Third Party **/
    @PostMapping("/new-transference/from/{name}/{hashKey}")
    @ResponseStatus(HttpStatus.CREATED)
    public Movement transfer(@PathVariable String name,@PathVariable String hashKey, @RequestBody @Valid MovementDTO movementDTO){
        return accountService.transferFromThirdParty(name, hashKey, movementDTO);
    }


}
