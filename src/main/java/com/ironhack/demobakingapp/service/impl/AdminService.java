package com.ironhack.demobakingapp.service.impl;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.controller.DTO.BalanceDTO;
import com.ironhack.demobakingapp.controller.DTO.SavingsDTO;
import com.ironhack.demobakingapp.enums.UserRole;
import com.ironhack.demobakingapp.model.Account;
import com.ironhack.demobakingapp.model.AccountHolder;
import com.ironhack.demobakingapp.model.Savings;
import com.ironhack.demobakingapp.repository.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.AccountRepository;
import com.ironhack.demobakingapp.repository.AdminRepository;
import com.ironhack.demobakingapp.service.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private SavingsService savingsService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;


    public BalanceDTO checkBalance(Long accountHolderId) {
        AccountHolder accountHolder = accountHolderRepository.findById(accountHolderId).get();

        Set<Account> accountList = accountHolder.showAccounts();

        BalanceDTO balanceDTO = new BalanceDTO();

        if(accountList.size() != 0){
            for (Account account: accountList) {
                balanceDTO.setAccountId(account.getId());
                balanceDTO.setBalanceAmount(account.getBalance().getAmount());
                balanceDTO.setBalanceCurrency(account.getBalance().getCurrency());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "This user does not have accounts");
        }

        return balanceDTO;
    }


}
