package com.ironhack.demobakingapp.controller.impl;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.controller.DTO.*;
import com.ironhack.demobakingapp.enums.UserRole;
import com.ironhack.demobakingapp.model.*;
import com.ironhack.demobakingapp.repository.*;
import com.ironhack.demobakingapp.security.CustomUserDetails;
import com.ironhack.demobakingapp.service.impl.*;
import com.ironhack.demobakingapp.service.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
public class AdminController {

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
        List<BalanceDTO> balanceDTOList = new ArrayList<>();
        if (adminRepository.findById(id).isPresent() && principal.getName().equals(adminRepository.findById(id).get().getUsername())) {
             List<Account> accounts = accountRepository.findAll();
            for (Account account: accounts) {
                BalanceDTO balanceDTO = new BalanceDTO(account.getId(), account.getBalance().getAmount(), account.getBalance().getCurrency());
                balanceDTOList.add(balanceDTO);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Admin id not found");
        }
        return balanceDTOList;
    }

    /** Show All Admins **/
    @GetMapping("/admin/info")
    @ResponseStatus(HttpStatus.OK)
    public List<Admin> findAllAdmins(){
        return adminRepository.findAll();
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
    public Savings create(@PathVariable Long id, @RequestBody SavingsDTO savingsDTO, Principal principal) {
        if (adminRepository.findById(id).isPresent() && principal.getName().equals(adminRepository.findById(id).get().getUsername())) {
            return savingsService.add(savingsDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The id does not match an Admin Account");
        }
    }

    /** Create New Checking Account **/
    @PostMapping("/admin/checkings/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentChecking addStudentCheking(@PathVariable Long id, @RequestBody CheckingDTO checkingDTO, Principal principal) {
        if (adminRepository.findById(id).isPresent() && principal.getName().equals(adminRepository.findById(id).get().getUsername())) {
            return studentCheckingService.add(checkingDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The id does not match an Admin Account");
        }
    }

    /** Create New Credit Card Account **/
    @PostMapping("/admin/credit-card/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard addCreditCard(@PathVariable Long id, @RequestBody CreditCardDTO creditCardDTO, Principal principal) {
        if (adminRepository.findById(id).isPresent() && principal.getName().equals(adminRepository.findById(id).get().getUsername())) {
            return creditCardService.add(creditCardDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The id does not match an Admin Account");
        }
    }

    /** POST REQUEST USERS **/

    /** New Admin **/
    @PostMapping("/new/admin/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin add(@PathVariable Long id, @RequestBody AdminDTO adminDTO, Principal principal){
        if (adminRepository.findById(id).isPresent() && principal.getName().equals(adminRepository.findById(id).get().getUsername())) {
            Admin admin = new Admin(adminDTO.getName(), adminDTO.getPassword(), adminDTO.getUsername());
            Role role = new Role(UserRole.ADMIN, admin);
            Set<Role> roles = Stream.of(role).collect(Collectors.toCollection(HashSet::new));
            admin.setRoles(roles);
            return adminRepository.save(admin);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The id does not match an Admin Account");
        }
    }

    /** New Admin For FREE**/
    @PostMapping("/for-free/")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin addAdmin(@RequestBody AdminDTO adminDTO){
        Admin admin = new Admin(adminDTO.getName(), adminDTO.getPassword(), adminDTO.getUsername());
            Role role = new Role(UserRole.ADMIN, admin);
            Set<Role> roles = Stream.of(role).collect(Collectors.toCollection(HashSet::new));
            admin.setRoles(roles);
            return adminRepository.save(admin);
    }

    /** New Account Holder **/
    @PostMapping("/new/account-holder/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder add(@PathVariable Long id, @RequestBody AccountHolder accountHolder, Principal principal){
        if (adminRepository.findById(id).isPresent() && principal.getName().equals(adminRepository.findById(id).get().getUsername())){
            return accountHolderRepository.save(accountHolder);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The id does not match an Admin Account");
        }
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
