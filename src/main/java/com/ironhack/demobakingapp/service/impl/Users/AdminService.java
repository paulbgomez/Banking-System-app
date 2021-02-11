package com.ironhack.demobakingapp.service.impl.Users;

import com.ironhack.demobakingapp.controller.DTO.Users.AccountHolderDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.CheckingDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.CreditCardDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.SavingsDTO;
import com.ironhack.demobakingapp.controller.DTO.Transferences.BalanceDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.AdminDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.ThirdPartyDTO;
import com.ironhack.demobakingapp.enums.UserRole;
import com.ironhack.demobakingapp.model.Accounts.Account;
import com.ironhack.demobakingapp.model.Accounts.CreditCard;
import com.ironhack.demobakingapp.model.Accounts.Savings;
import com.ironhack.demobakingapp.model.Accounts.StudentChecking;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.model.Users.Admin;
import com.ironhack.demobakingapp.model.Users.Role;
import com.ironhack.demobakingapp.model.Users.ThirdParty;
import com.ironhack.demobakingapp.repository.Users.AdminRepository;
import com.ironhack.demobakingapp.service.impl.Accounts.AccountService;
import com.ironhack.demobakingapp.service.impl.Accounts.CreditCardService;
import com.ironhack.demobakingapp.service.impl.Accounts.StudentCheckingService;
import com.ironhack.demobakingapp.service.interfaces.Accounts.ISavingsService;
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
    private ISavingsService savingsService;

    @Autowired
    private StudentCheckingService studentCheckingService;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountHolderService accountHolderService;

    @Autowired
    private ThirdPartyService thirdPartyService;

    public Admin findByUsername(String username){
        return adminRepository.findByUsername(username);
    }

    public List<Admin> findAll(){
        return adminRepository.findAll();
    }

    public Admin save(Admin admin){
        return adminRepository.save(admin);
    }

    public Optional<Admin> findOptionalAdminById(Long id){
        return adminRepository.findById(id);
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

    /** Create a new Third Party **/
    public ThirdParty addThirdParty(Long id, ThirdPartyDTO thirdPartyDTO, String username) {
        if (adminRepository.findById(id).isPresent() && username.equals(adminRepository.findById(id).get().getUsername())) {
            return thirdPartyService.add(thirdPartyDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The id does not match an Admin Account");
        }
    }
}
