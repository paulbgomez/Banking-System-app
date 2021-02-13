package com.ironhack.demobakingapp.service.interfaces.Users;

import com.ironhack.demobakingapp.controller.DTO.Accounts.CheckingDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.CreditCardDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.SavingsDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.AccountHolderDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.AdminDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.ThirdPartyDTO;
import com.ironhack.demobakingapp.model.Accounts.CreditCard;
import com.ironhack.demobakingapp.model.Accounts.Savings;
import com.ironhack.demobakingapp.model.Accounts.StudentChecking;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.model.Users.Admin;
import com.ironhack.demobakingapp.model.Users.ThirdParty;
import java.util.List;
import java.util.Optional;

public interface IAdminService {

    Admin findByUsername(String username);

    List<Admin> findAll();

    Admin save(Admin admin);

    Optional<Admin> findOptionalAdminById(Long id);

    Savings addSavingsAccount(Long id, SavingsDTO savingsDTO, String username);

    StudentChecking addCheckingAccount(Long id, CheckingDTO checkingDTO, String username);

    CreditCard addCreditCardAccount(Long id, CreditCardDTO creditCardDTO, String username);

    Admin addAdmin(Long id, AdminDTO adminDTO, String username);

    AccountHolder addAccountHolder(Long id, AccountHolderDTO accountHolderDTO, String username);

    ThirdParty addThirdParty(Long id, ThirdPartyDTO thirdPartyDTO, String username);




}
