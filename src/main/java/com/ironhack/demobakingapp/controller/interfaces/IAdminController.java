package com.ironhack.demobakingapp.controller.interfaces;

import com.ironhack.demobakingapp.controller.DTO.Users.AccountHolderDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.CheckingDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.CreditCardDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.SavingsDTO;
import com.ironhack.demobakingapp.controller.DTO.Transferences.BalanceDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.AdminDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.ThirdPartyDTO;
import com.ironhack.demobakingapp.model.Accounts.CreditCard;
import com.ironhack.demobakingapp.model.Accounts.Savings;
import com.ironhack.demobakingapp.model.Accounts.StudentChecking;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.model.Users.Admin;
import com.ironhack.demobakingapp.model.Users.ThirdParty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface IAdminController {

    /** GET REQUESTS **/

    /** Show All Accounts **/
    List<BalanceDTO> checkBalanceAll(Long id, Principal principal);

    /** Show All Admins **/
    List<Admin> findAllAdmins();

    /** Show All Savings Accounts **/
    List<Savings> findAllSavingAccounts();

    /** Show One Savings Account Balance For Admin **/
    BalanceDTO checkBalanceAdmin( Long id, Principal principal);

    /** POST REQUEST ACCOUNTS **/

    /** Create New Savings Account **/
    Savings addSavingsAccount(Long id, SavingsDTO savingsDTO, Principal principal);

    /** Create New Checking Account **/
    StudentChecking addCheckingAccount(Long id, CheckingDTO checkingDTO, Principal principal);

    /** Create New Credit Card Account **/
    CreditCard addCreditCardAccount(Long id, CreditCardDTO creditCardDTO, Principal principal);

    /** POST REQUEST USERS **/

    /** New Admin **/
    Admin addAdmin(Long id, AdminDTO adminDTO, Principal principal);

    /** New Admin For FREE**/
    Admin ass(AdminDTO adminDTO);

    /** New Account Holder **/
    AccountHolder addAccountHolder(Long id, AccountHolderDTO accountHolderDTO, Principal principal);

    /** New Third Party **/
    ThirdParty addThirdParty(Long id, ThirdPartyDTO thirdPartyDTO, Principal principal);

    /** PUT REQUESTS **/

    void incrementBalance(Long id, BigDecimal amount, Principal principal);

    void decrementBalance(Long id, BigDecimal amount, Principal principal);

}
