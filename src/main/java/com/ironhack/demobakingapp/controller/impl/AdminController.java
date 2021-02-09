package com.ironhack.demobakingapp.controller.impl;

import com.ironhack.demobakingapp.controller.DTO.AdminDTO;
import com.ironhack.demobakingapp.controller.DTO.BalanceDTO;
import com.ironhack.demobakingapp.enums.UserRole;
import com.ironhack.demobakingapp.model.Account;
import com.ironhack.demobakingapp.model.AccountHolder;
import com.ironhack.demobakingapp.model.Admin;
import com.ironhack.demobakingapp.model.Role;
import com.ironhack.demobakingapp.repository.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.AccountRepository;
import com.ironhack.demobakingapp.repository.AdminRepository;
import com.ironhack.demobakingapp.security.CustomUserDetails;
import com.ironhack.demobakingapp.service.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @GetMapping("/account-balance/{id1}/single/{id2}")
    @ResponseStatus(HttpStatus.OK)
    public BalanceDTO checkBalanceSingle(@PathVariable Long id1, @PathVariable Long id2, Principal principal) {
        if (adminRepository.findById(id2).isPresent() && principal.getName().equals(adminRepository.findById(id2).get().getUsername())) {
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
    public List<Admin> findAll(){
        return adminRepository.findAll();
    }

    //OK crea admins con admins
    @PostMapping("/new/admin/")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin add(@RequestBody AdminDTO adminDTO){
        Admin admin = new Admin(adminDTO.getName(), adminDTO.getPassword(), adminDTO.getUsername());
        Role role = new Role(UserRole.ADMIN, admin);
        Set<Role> roles = Stream.of(role).collect(Collectors.toCollection(HashSet::new));
        admin.setRoles(roles);
        return adminRepository.save(admin);
    }

    //Crea account holder con admin
    @PostMapping("/new/account-holder/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder add(@PathVariable Long id, @RequestBody AccountHolder accountHolder, Principal principal){
        if (adminRepository.findById(id).isPresent() && principal.getName().equals(adminRepository.findById(id).get().getUsername())){
           return accountHolderRepository.save(accountHolder);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found for an admin");
        }
    }


}
