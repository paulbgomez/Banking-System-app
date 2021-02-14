package com.ironhack.demobakingapp.service.interfaces.Accounts;

import com.ironhack.demobakingapp.model.Accounts.Checking;

public interface ICheckingService {

    Checking findById(Long id);
    void applyMonthlyFee(Long id);

}
