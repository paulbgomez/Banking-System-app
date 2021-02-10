package com.ironhack.demobakingapp.service.impl;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.classes.Years;
import com.ironhack.demobakingapp.controller.DTO.StudentCheckingDTO;
import com.ironhack.demobakingapp.model.AccountHolder;
import com.ironhack.demobakingapp.model.Checking;
import com.ironhack.demobakingapp.model.StudentChecking;
import com.ironhack.demobakingapp.repository.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.StudentCheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.ironhack.demobakingapp.service.impl.SavingsService.years;

@Service
public class StudentCheckingService {

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    public StudentChecking add(StudentCheckingDTO studentCheckingDTO) {

        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(studentCheckingDTO.getPrimaryOwnerId());
        AccountHolder accountHolder1 = studentCheckingDTO.getSecondaryOwnerId() != null ?
                accountHolderRepository.findById(studentCheckingDTO.getSecondaryOwnerId()).get() :
                null;

        StudentChecking studentChecking = new StudentChecking();

        if (accountHolder.isPresent() && (Years.years(accountHolder.get().getBirthDate()) < 25)) {
            studentChecking.setBalance(new Money(studentCheckingDTO.getBalance()));
            studentChecking.setPrimaryOwner(accountHolder.get());
            studentChecking.setStatus(studentCheckingDTO.getStatus());
            studentChecking.setSecretKey(studentCheckingDTO.getSecretKey());

        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The account holder does not exist or you are older than 25");
        }

        if (accountHolder1 != null) {
            studentChecking.setSecondaryOwner(accountHolder1);
        }

        return studentCheckingRepository.save(studentChecking);
    }



}
