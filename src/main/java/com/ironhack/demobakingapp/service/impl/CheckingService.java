package com.ironhack.demobakingapp.service.impl;

import com.ironhack.demobakingapp.repository.CheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckingService {

    @Autowired
    private CheckingRepository checkingRepository;

}
