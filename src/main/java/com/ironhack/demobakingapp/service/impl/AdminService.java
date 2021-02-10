package com.ironhack.demobakingapp.service.impl;

import com.ironhack.demobakingapp.controller.DTO.*;
import com.ironhack.demobakingapp.enums.UserRole;
import com.ironhack.demobakingapp.model.Accounts.Account;
import com.ironhack.demobakingapp.model.Accounts.CreditCard;
import com.ironhack.demobakingapp.model.Accounts.Savings;
import com.ironhack.demobakingapp.model.Accounts.StudentChecking;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.model.Users.Admin;
import com.ironhack.demobakingapp.model.Users.Role;
import com.ironhack.demobakingapp.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private SavingsService savingsService;

    @Autowired
    private StudentCheckingService studentCheckingService;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountHolderService accountHolderService;


    public Admin findByUsername(String username){
        return adminRepository.findByUsername(username);
    }

    public List<Admin> findAll(){
        return adminRepository.findAll();
    }

    public Optional<Admin> findOptionalAdminById(Long id){
        return adminRepository.findById(id);
    }

    /** Check balance of any Account with a given ID **/
    public BalanceDTO checkBalance(Long accountHolderId) {
        AccountHolder accountHolder = accountHolderService.findById(accountHolderId);

        Set<Account> accountList = accountHolder.showAccounts();

        BalanceDTO balanceDTO = new BalanceDTO();

        if(accountList.size() != 0){
            for (Account account: accountList) {
                balanceDTO.setAccountId(account.getId());
                balanceDTO.setBalanceAmount(account.getBalance().getAmount());
                balanceDTO.setBalanceCurrency(account.getBalance().getCurrency());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "This user does not have accounts");
        }

        return balanceDTO;
    }

    /** Create a Savings Account **/
    public Savings addSavingsAccount(Long id, SavingsDTO savingsDTO, String username) {
        if (adminRepository.findById(id).isPresent() && username.equals(adminRepository.findById(id).get().getUsername())) {
            return savingsService.add(savingsDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The id does not match an Admin Account");
        }
    }

    /** Create a Student Checking or Checking Account **/
    public StudentChecking addCheckingAccount(Long id, CheckingDTO checkingDTO, String username){
        if (adminRepository.findById(id).isPresent() && username.equals(adminRepository.findById(id).get().getUsername())) {
            return studentCheckingService.add(checkingDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The id does not match an Admin Account");
        }
    }

    /** Create a Credit Card Account **/
    public CreditCard addCreditCardAccount(Long id, CreditCardDTO creditCardDTO, String username){
        if (adminRepository.findById(id).isPresent() && username.equals(adminRepository.findById(id).get().getUsername())) {
            return creditCardService.add(creditCardDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The id does not match an Admin Account");
        }
    }

    /** Create a new Admin User **/
    public Admin addAdmin(Long id, AdminDTO adminDTO, String username){
        if (adminRepository.findById(id).isPresent() && username.equals(adminRepository.findById(id).get().getUsername())) {
            Admin admin = new Admin(adminDTO.getName(), adminDTO.getPassword(), adminDTO.getUsername());
            Role role = new Role(UserRole.ADMIN, admin);
            Set<Role> roles = Stream.of(role).collect(Collectors.toCollection(HashSet::new));
            admin.setRoles(roles);
            return adminRepository.save(admin);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The id does not match an Admin Account");
        }
    }

    /** Create a new Account Holder **/
    public AccountHolder addAccountHolder(Long id, AccountHolderDTO accountHolderDTO, String username){
        if (adminRepository.findById(id).isPresent() && username.equals(adminRepository.findById(id).get().getUsername())) {
            return accountHolderService.add(accountHolderDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The id does not match an Admin Account");
        }
    }

}
