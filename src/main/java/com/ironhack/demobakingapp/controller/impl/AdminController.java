package com.ironhack.demobakingapp.controller.impl;

import com.ironhack.demobakingapp.controller.DTO.Users.AccountHolderDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.CheckingDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.CreditCardDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.SavingsDTO;
import com.ironhack.demobakingapp.controller.DTO.Transferences.BalanceDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.AdminDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.ThirdPartyDTO;
import com.ironhack.demobakingapp.controller.interfaces.IAdminController;
import com.ironhack.demobakingapp.enums.UserRole;
import com.ironhack.demobakingapp.model.Accounts.CreditCard;
import com.ironhack.demobakingapp.model.Accounts.Savings;
import com.ironhack.demobakingapp.model.Accounts.StudentChecking;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.model.Users.Admin;
import com.ironhack.demobakingapp.model.Users.Role;
import com.ironhack.demobakingapp.model.Users.ThirdParty;
import com.ironhack.demobakingapp.service.impl.Accounts.AccountService;
import com.ironhack.demobakingapp.service.impl.Accounts.CreditCardService;
import com.ironhack.demobakingapp.service.impl.Accounts.StudentCheckingService;
import com.ironhack.demobakingapp.service.impl.Users.AdminService;
import com.ironhack.demobakingapp.service.interfaces.Accounts.IAccountService;
import com.ironhack.demobakingapp.service.interfaces.Accounts.ICreditCardService;
import com.ironhack.demobakingapp.service.interfaces.Accounts.ISavingsService;
import com.ironhack.demobakingapp.service.interfaces.Accounts.IStudentCheckingService;
import com.ironhack.demobakingapp.service.interfaces.Users.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class AdminController implements IAdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IStudentCheckingService studentCheckingService;

    @Autowired
    private ISavingsService savingsService;

    @Autowired
    private ICreditCardService creditCardService;

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

    /** Show One Savings Account Balance For Admin Logger **/
    @GetMapping("/admin/savings/check-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BalanceDTO checkBalanceAdmin(@PathVariable Long id, Principal principal){
        return savingsService.checkBalanceAdmin(id, principal.getName());
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

    /** New Account Holder **/
    @PostMapping("/new/account-holder/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder addAccountHolder(@PathVariable Long id, @RequestBody @Valid AccountHolderDTO accountHolderDTO, Principal principal){
        return adminService.addAccountHolder(id, accountHolderDTO, principal.getName());
    }

    /** New Third Party **/
    @PostMapping("/new/third-party/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty addThirdParty(@PathVariable Long id, @RequestBody @Valid ThirdPartyDTO thirdPartyDTO, Principal principal){
        return adminService.addThirdParty(id, thirdPartyDTO, principal.getName());
    }

    /** PUT REQUESTS **/

    /** Increment Balance Amount **/
    @PutMapping("/admin/account/increment/{id}/{amount}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void incrementBalance(@PathVariable Long id, @PathVariable BigDecimal amount, Principal principal){
        Optional<Admin> admin = Optional.ofNullable(adminService.findByUsername(principal.getName()));
        if(admin.isPresent()){
            accountService.incrementBalance(id, amount, principal.getName());
        }
    }

    /** Decrement Balance Amount **/
    @PutMapping("/admin/account/decrement/{id}/{amount}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void decrementBalance(@PathVariable Long id, @PathVariable BigDecimal amount, Principal principal){
        Optional<Admin> admin = Optional.ofNullable(adminService.findByUsername(principal.getName()));
        if(admin.isPresent()){
            accountService.decrementBalance(id, amount, principal.getName());
        }
    }


}
