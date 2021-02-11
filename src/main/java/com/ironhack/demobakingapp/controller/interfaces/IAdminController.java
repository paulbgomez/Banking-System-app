package com.ironhack.demobakingapp.controller.interfaces;

import com.ironhack.demobakingapp.controller.DTO.Users.AccountHolderDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.CheckingDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.CreditCardDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.SavingsDTO;
import com.ironhack.demobakingapp.controller.DTO.Transferences.BalanceDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.AdminDTO;
import com.ironhack.demobakingapp.model.Accounts.CreditCard;
import com.ironhack.demobakingapp.model.Accounts.Savings;
import com.ironhack.demobakingapp.model.Accounts.StudentChecking;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.model.Users.Admin;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface IAdminController {

    /** GET REQUESTS **/

    /** Show All Accounts **/
    public List<BalanceDTO> checkBalanceAll(Long id, Principal principal);

    /** Show All Admins **/
    public List<Admin> findAllAdmins();

    /** Show All Savings Accounts **/
    public List<Savings> findAllSavingAccounts();

    /** Show One Savings Account Balance For Admin **/
    public BalanceDTO checkBalanceAdmin( Long id, Principal principal);

    /** CHANGE TO ANOTHER CONTROLLER **/
    /** Show Savings Account by Id **/
    public Savings findSavingsById( Long id);

    /** Show One Savings Account Balance For Account Holder **/
    public BalanceDTO checkBalance(Long id, Principal principal);

    /** POST REQUEST ACCOUNTS **/

    /** Create New Savings Account **/
    public Savings addSavingsAccount(Long id, SavingsDTO savingsDTO, Principal principal);

    /** Create New Checking Account **/
    public StudentChecking addCheckingAccount(Long id, CheckingDTO checkingDTO, Principal principal);

    /** Create New Credit Card Account **/
    public CreditCard addCreditCardAccount(Long id, CreditCardDTO creditCardDTO, Principal principal);

    /** POST REQUEST USERS **/

    /** New Admin **/
    public Admin addAdmin(Long id, AdminDTO adminDTO, Principal principal);

    /** New Admin For FREE**/
    public Admin ass(AdminDTO adminDTO);

    /** New Account Holder **/
    public AccountHolder addAccountHolder(Long id, AccountHolderDTO accountHolderDTO, Principal principal);


    /** PUT REQUESTS **/

    public void incrementBalance(Long id, BigDecimal amount, Principal principal);

}
