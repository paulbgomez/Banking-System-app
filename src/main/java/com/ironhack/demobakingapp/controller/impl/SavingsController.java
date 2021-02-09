package com.ironhack.demobakingapp.controller.impl;


import com.ironhack.demobakingapp.controller.DTO.SavingsDTO;
import com.ironhack.demobakingapp.controller.interfaces.ISavingsController;
import com.ironhack.demobakingapp.model.Savings;
import com.ironhack.demobakingapp.service.interfaces.ISavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class SavingsController implements ISavingsController {

    @Autowired
    private ISavingsService savingsService;

    @PostMapping("/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings add(@RequestBody @Valid SavingsDTO savingsDTO){
        return savingsService.add(savingsDTO);
    }

    @GetMapping("/savings")
    @ResponseStatus(HttpStatus.OK)
    public List<Savings> findAll() {
        return savingsService.findAll();
    }

}
