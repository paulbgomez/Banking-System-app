package com.ironhack.demobakingapp.service.impl.Accounts;

import com.ironhack.demobakingapp.classes.Time;
import com.ironhack.demobakingapp.model.Accounts.Checking;
import com.ironhack.demobakingapp.repository.Accounts.CheckingRepository;
import com.ironhack.demobakingapp.service.interfaces.Accounts.ICheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CheckingService implements ICheckingService {

    @Autowired
    private CheckingRepository checkingRepository;

    public Checking findById(Long id){
        return checkingRepository.findById(id).get();
    }

    /** Apply the monthly fee to the checking account **/
    public void applyMonthlyFee(Long id){
        Optional<Checking> checking = checkingRepository.findById(id);
        Integer month = Time.months(checking.get().getLastFee().toLocalDate());

        if (checking.isPresent() && month >= 1){

            checking.get().getBalance().decreaseAmount(checking.get().getMonthlyFee());
            checking.get().setLastFee(checking.get().getLastFee().plusMonths(Time.months(checking.get().getLastFee().toLocalDate())));
        }
        checkingRepository.save(checking.get());
    }
}
