package com.ironhack.demobakingapp.controller.impl;

import com.ironhack.demobakingapp.controller.DTO.*;
import com.ironhack.demobakingapp.controller.interfaces.IAdminController;
import com.ironhack.demobakingapp.enums.UserRole;
import com.ironhack.demobakingapp.model.Accounts.CreditCard;
import com.ironhack.demobakingapp.model.Accounts.Savings;
import com.ironhack.demobakingapp.model.Accounts.StudentChecking;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.model.Users.Admin;
import com.ironhack.demobakingapp.model.Users.Role;
import com.ironhack.demobakingapp.repository.*;
import com.ironhack.demobakingapp.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class AdminController implements IAdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private StudentCheckingService studentCheckingService;

    @Autowired
    private SavingsService savingsService;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private CreditCardService creditCardService;

    /** GET REQUEST **/

    /** Show All Accounts **/
    @GetMapping("/admin/account-balance/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<BalanceDTO> checkBalanceAll(@PathVariable Long id, Principal principal) {
        return accountService.checkBalanceAll(id, principal.getName());
    }

    /** Show All Admins **/
    @GetMapping("/admin/info")
    @ResponseStatus(HttpStatus.OK)
    public List<Admin> findAllAdmins(){
        return adminService.findAll();
    }

    /** Show All Savings Accounts **/
    @GetMapping("/admin/savings")
    @ResponseStatus(HttpStatus.OK)
    public List<Savings> findAllSavingAccounts(){ return savingsService.findAll();}

    /** Show One Savings Account Balance For Admin **/
    @GetMapping("/admin/savings/check-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BalanceDTO checkBalanceAdmin(@PathVariable Long id, Principal principal){
        return savingsService.checkBalanceAdmin(id, principal.getName());
    }

    /** CHANGE TO ANOTHER CONTROLLER **/
    /** Show Savings Account by Id **/
    @GetMapping("/savings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Savings findSavingsById(@PathVariable Long id){ return savingsService.findById(id);}

    /** Show One Savings Account Balance For Account Holder **/
    @GetMapping("/savings/check-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BalanceDTO checkBalance(@PathVariable Long id, Principal principal){
        return savingsService.checkBalance(id, principal.getName());
    }

    /** POST REQUEST ACCOUNTS **/

    /** Create New Savings Account **/
    @PostMapping("/admin/savings/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings addSavingsAccount(@PathVariable Long id, @RequestBody @Valid SavingsDTO savingsDTO, Principal principal) {
        return adminService.addSavingsAccount(id, savingsDTO, principal.getName());
    }

    /** Create New Checking Account **/
    @PostMapping("/admin/checkings/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentChecking addCheckingAccount(@PathVariable Long id, @RequestBody @Valid CheckingDTO checkingDTO, Principal principal) {
        return adminService.addCheckingAccount(id, checkingDTO, principal.getName());
    }

    /** Create New Credit Card Account **/
    @PostMapping("/admin/credit-card/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard addCreditCardAccount(@PathVariable Long id, @RequestBody @Valid CreditCardDTO creditCardDTO, Principal principal) {
        return adminService.addCreditCardAccount(id, creditCardDTO, principal.getName());
    }

    /** POST REQUEST USERS **/

    /** New Admin **/
    @PostMapping("/new/admin/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin addAdmin(@PathVariable Long id, @RequestBody @Valid AdminDTO adminDTO, Principal principal){
        return adminService.addAdmin(id, adminDTO, principal.getName());
    }

    /** New Admin For FREE**/
    @PostMapping("/for-free/")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin ass(@RequestBody AdminDTO adminDTO){
        Admin admin = new Admin(adminDTO.getName(), adminDTO.getPassword(), adminDTO.getUsername());
            Role role = new Role(UserRole.ADMIN, admin);
            Set<Role> roles = Stream.of(role).collect(Collectors.toCollection(HashSet::new));
            admin.setRoles(roles);
            return adminRepository.save(admin);
    }

    /** New Account Holder **/
    @PostMapping("/new/account-holder/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder addAccountHolder(@PathVariable Long id, @RequestBody @Valid AccountHolderDTO accountHolderDTO, Principal principal){
        return adminService.addAccountHolder(id, accountHolderDTO, principal.getName());
    }

    /** PUT REQUESTS **/

    /** Increment Balance Amount **/
    @PutMapping("/admin/account/{id}/{amount}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void incrementBalance(@PathVariable Long id, @PathVariable BigDecimal amount, Principal principal){
        Optional<Admin> admin = Optional.ofNullable(adminRepository.findByUsername(principal.getName()));
        if(admin.isPresent()){
            accountService.incrementBalance(id, amount, principal.getName());
        }
    }


}
