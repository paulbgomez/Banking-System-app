package com.ironhack.demobakingapp.service.impl.Accounts;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.controller.DTO.Transferences.BalanceDTO;
import com.ironhack.demobakingapp.controller.DTO.Transferences.MovementDTO;
import com.ironhack.demobakingapp.model.*;
import com.ironhack.demobakingapp.model.Accounts.Account;
import com.ironhack.demobakingapp.model.Accounts.CreditCard;
import com.ironhack.demobakingapp.model.Accounts.Savings;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.model.Users.Admin;
import com.ironhack.demobakingapp.model.Users.User;
import com.ironhack.demobakingapp.repository.*;
import com.ironhack.demobakingapp.repository.Accounts.AccountRepository;
import com.ironhack.demobakingapp.repository.Accounts.CreditCardRepository;
import com.ironhack.demobakingapp.repository.Accounts.SavingsRepository;
import com.ironhack.demobakingapp.repository.Accounts.StudentCheckingRepository;
import com.ironhack.demobakingapp.repository.Users.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.Users.AdminRepository;
import com.ironhack.demobakingapp.repository.Users.UserRepository;
import com.ironhack.demobakingapp.service.impl.Users.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private SavingsService savingsService;

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private CheckingService checkingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovementRepository movementRepository;

    public void incrementBalance(Long id, BigDecimal amount, String username){

        Account typeOfAccount = accountRepository.findById(id).get();

        User user = userRepository.findByUsername(username).get();

        Admin admin = adminService.findByUsername(user.getUsername());

        if (admin.getUsername().equals(username)) {
            if (typeOfAccount.getClass().equals(Savings.class)){
                typeOfAccount.getBalance().increaseAmount(amount);
                System.out.println(typeOfAccount.getBalance().toString() + " SAVINGS");
                savingsService.addInterestRate(id);
            } else if (typeOfAccount.getClass().equals(CreditCard.class)){
                System.out.println(typeOfAccount.getBalance().toString() + " CREDIT CARD");
                CreditCard creditCard = creditCardRepository.findById(id).get();
                creditCard.getBalance().increaseAmount(amount);
                //typeOfAccount.getBalance().increaseAmount(amount);
                creditCardService.addInterestRate(id);
            } else {
                System.out.println(typeOfAccount.getBalance().toString() + " CHECKING");
                typeOfAccount.getBalance().increaseAmount(amount);
            }
        }
    }

    public void decrementBalance(Long id, BigDecimal amount, String username){

        Account typeOfAccount = accountRepository.findById(id).get();

        User user = userRepository.findByUsername(username).get();

        Admin admin = adminService.findByUsername(user.getUsername());

        if (admin.getUsername().equals(username)) {
            if (typeOfAccount.getClass().equals(Savings.class)){
                typeOfAccount.setBalance(new Money(typeOfAccount.getBalance().decreaseAmount(amount)));
                savingsService.addInterestRate(id);
            } else if (typeOfAccount.getClass().equals(CreditCard.class)){
                typeOfAccount.setBalance(new Money(typeOfAccount.getBalance().decreaseAmount(amount)));
                creditCardService.addInterestRate(id);
            } else {
                typeOfAccount.setBalance(new Money(typeOfAccount.getBalance().decreaseAmount(amount)));
            }
        }
    }

    /** Method to transfer money between accounts **/
    public Movement transfer(MovementDTO movementDTO, String username) {
        AccountHolder user = accountHolderRepository.findByUsername(username).get();
        Account originAccount = accountRepository.findById(movementDTO.getSenderAccountId()).get();

        if(!user.showAccounts().contains(originAccount)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The origin account does not belong to the logged user");
        }
//        if(!fraudService.firstCondition(newTransference) || !fraudService.secondCondition(newTransference)) {
//            throw new FraudException("This transaction can not be done due to fraud detections"); }
        if(movementDTO.getAmount().compareTo(originAccount.getBalance().getAmount()) > 0 ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "There are not enough funds in the account");
        }

        Account destinationAccount = accountRepository.findById(movementDTO.getReceiverAccountId()).get();
        Money amount = new Money(movementDTO.getAmount());

        originAccount.getBalance().decreaseAmount(amount);
        destinationAccount.getBalance().increaseAmount(amount);
        Movement movement = new Movement(originAccount, destinationAccount, amount);

        return movementRepository.save(movement);
    }

    /** Method to check the balance of ALL accounts **/
    public List<BalanceDTO> checkBalanceAll(Long id, String username){
        List<BalanceDTO> balanceDTOList = new ArrayList<>();
        if (adminService.findOptionalAdminById(id).isPresent() && username.equals(adminService.findOptionalAdminById(id).get().getUsername())) {
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
}
