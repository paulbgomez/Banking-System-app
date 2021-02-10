package com.ironhack.demobakingapp.controller.impl;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.controller.DTO.AdminDTO;
import com.ironhack.demobakingapp.controller.DTO.BalanceDTO;
import com.ironhack.demobakingapp.controller.DTO.SavingsDTO;
import com.ironhack.demobakingapp.controller.DTO.StudentCheckingDTO;
import com.ironhack.demobakingapp.enums.UserRole;
import com.ironhack.demobakingapp.model.*;
import com.ironhack.demobakingapp.repository.*;
import com.ironhack.demobakingapp.security.CustomUserDetails;
import com.ironhack.demobakingapp.service.impl.SavingsService;
import com.ironhack.demobakingapp.service.impl.StudentCheckingService;
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

import static com.ironhack.demobakingapp.service.impl.SavingsService.years;

@RestController
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AccountRepository accountRepository;

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

    //enseña todas las cuentas
    @GetMapping("/account-balance/all/{id}")
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin id not found");
        }
        return balanceDTOList;
    }

    //enseña una las cuentas
    @GetMapping("/account-balance/{id1}/single/{id2}")
    @ResponseStatus(HttpStatus.OK)
    public BalanceDTO checkBalanceSingle(@PathVariable Long id1, @PathVariable Long id2, Principal principal) {
        if (adminRepository.existsById(id2) && principal.getName().equals(adminRepository.findById(id2).get().getUsername())) {
            Account account = accountRepository.findById(id1).get();
            BalanceDTO balanceDTO = new BalanceDTO(account.getId(), account.getBalance().getAmount(), account.getBalance().getCurrency());
            return balanceDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin id not found");
        }
    }

    //devuelve todos los admins
    @GetMapping("/admin/account")
    @ResponseStatus(HttpStatus.OK)
    public List<Admin> findAllAdmins(){
        return adminRepository.findAll();
    }

    //OK crea admins con admins
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

    //Crea account holder con admin
    @PostMapping("/new/account-holder/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder add(@PathVariable Long id, @RequestBody AccountHolder accountHolder, Principal principal){
        if (adminRepository.findById(id).isPresent() && principal.getName().equals(adminRepository.findById(id).get().getUsername())){
           return accountHolderRepository.save(accountHolder);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The id does not match an Admin Account");
        }
    }

    //crea savings con admin
    @PostMapping("/admin/savings/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings create(@PathVariable Long id, @RequestBody SavingsDTO savingsDTO, Principal principal) {
        if (adminRepository.findById(id).isPresent() && principal.getName().equals(adminRepository.findById(id).get().getUsername())) {
            return savingsService.add(savingsDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The id does not match an Admin Account");
        }
    }

    //enseña todas las saving accounts
    @GetMapping("/savings")
    @ResponseStatus(HttpStatus.OK)
    public List<Savings> findAllSavingAccounts(){ return savingsService.findAll();}

    //enseña el savings por id
    @GetMapping("/savings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Savings findSavingsById(@PathVariable Long id){ return savingsService.findById(id);}

    //checkea el balance de una savings account si eres accountholder
    @GetMapping("/check-balance/savings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BalanceDTO checkBalance(@PathVariable Long id, Principal principal){
        return savingsService.checkBalance(id, principal.getName());
    }

    //check balance with admin
    @GetMapping("/check-balance/admin/savings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BalanceDTO checkBalanceAdmin(@PathVariable Long id, Principal principal){
        return savingsService.checkBalanceAdmin(id, principal.getName());
    }

    @PostMapping("/admin/checkings/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentChecking create(@PathVariable Long id, @RequestBody StudentCheckingDTO studentCheckingDTO, Principal principal) {
        if (adminRepository.findById(id).isPresent() && principal.getName().equals(adminRepository.findById(id).get().getUsername())) {
            return studentCheckingService.add(studentCheckingDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The id does not match an Admin Account");
        }
    }


}
