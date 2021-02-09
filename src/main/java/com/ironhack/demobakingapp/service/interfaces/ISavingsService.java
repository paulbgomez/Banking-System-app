package com.ironhack.demobakingapp.service.interfaces;

import com.ironhack.demobakingapp.controller.DTO.SavingsDTO;
import com.ironhack.demobakingapp.model.Savings;

import java.util.List;

public interface ISavingsService {

    public Savings add(SavingsDTO savingsDTO);
    public List<Savings> findAll();
    public Savings findById(Long id);

}
