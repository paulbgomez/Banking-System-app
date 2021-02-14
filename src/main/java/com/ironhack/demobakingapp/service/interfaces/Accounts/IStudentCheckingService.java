package com.ironhack.demobakingapp.service.interfaces.Accounts;

import com.ironhack.demobakingapp.controller.DTO.Accounts.CheckingDTO;
import com.ironhack.demobakingapp.model.Accounts.StudentChecking;

public interface IStudentCheckingService {
    StudentChecking findById(Long id);

    StudentChecking add(CheckingDTO checkingDTO);

    }
