package com.ironhack.demobakingapp.service.impl.Accounts;

import com.ironhack.demobakingapp.model.Accounts.Checking;
import com.ironhack.demobakingapp.repository.Accounts.CheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckingService {

    @Autowired
    private CheckingRepository checkingRepository;

    public Checking findById(Long id){
        return checkingRepository.findById(id).get();
    }
}
