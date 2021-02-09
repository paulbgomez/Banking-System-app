package com.ironhack.demobakingapp.controller.interfaces;

import com.ironhack.demobakingapp.controller.DTO.SavingsDTO;
import com.ironhack.demobakingapp.model.Savings;

import java.util.List;

public interface ISavingsController {

    public Savings add(SavingsDTO savingsDTO);
    public List<Savings> findAll();
}
