package com.ironhack.demobakingapp.service.impl.Accounts;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.classes.Time;
import com.ironhack.demobakingapp.controller.DTO.Accounts.CheckingDTO;
import com.ironhack.demobakingapp.model.Accounts.Checking;
import com.ironhack.demobakingapp.model.Accounts.StudentChecking;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.repository.Accounts.CheckingRepository;
import com.ironhack.demobakingapp.repository.Accounts.StudentCheckingRepository;
import com.ironhack.demobakingapp.repository.Users.AccountHolderRepository;
import com.ironhack.demobakingapp.service.interfaces.Accounts.IStudentCheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StudentCheckingService implements IStudentCheckingService {

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    /** Find a checking account by Id **/
    public StudentChecking findById(Long id){
        return studentCheckingRepository.findById(id).get();
    }

    /** Add a new checking or student checking account. It depends on the account holder age **/
    public StudentChecking add(CheckingDTO checkingDTO) {

        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(checkingDTO.getPrimaryOwnerId());
        AccountHolder accountHolder1 = checkingDTO.getSecondaryOwnerId() != null ?
                accountHolderRepository.findById(checkingDTO.getSecondaryOwnerId()).get() :
                null;

        StudentChecking studentChecking = new StudentChecking();
        Checking checking = new Checking();

        if (accountHolder.isPresent() && (Time.years(accountHolder.get().getBirthDate()) < 25)) {
            studentChecking.setBalance(new Money(checkingDTO.getBalance()));
            studentChecking.setPrimaryOwner(accountHolder.get());
            studentChecking.setCreationTime(LocalDateTime.now());
            studentChecking.setStatus(checkingDTO.getStatus());
            studentChecking.setSecretKey(checkingDTO.getSecretKey());
            if (accountHolder1 != null) {
                studentChecking.setSecondaryOwner(accountHolder1);
            }
            return studentCheckingRepository.save(studentChecking);

        } else {
            checking.setBalance(new Money(checkingDTO.getBalance()));
            checking.setPrimaryOwner(accountHolder.get());
            checking.setCreationTime(LocalDateTime.now());
            checking.setStatus(checkingDTO.getStatus());
            checking.setSecretKey(checkingDTO.getSecretKey());
            checking.getMonthlyFee();
            checking.getMinimumBalance();

            if (accountHolder1 != null) {checking.setSecondaryOwner(accountHolder1);}

            return checkingRepository.save(checking);
        }
    }

}
