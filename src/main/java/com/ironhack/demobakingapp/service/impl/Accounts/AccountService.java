package com.ironhack.demobakingapp.service.impl.Accounts;

import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.controller.DTO.Transferences.BalanceDTO;
import com.ironhack.demobakingapp.controller.DTO.Transferences.MovementDTO;
import com.ironhack.demobakingapp.enums.Status;
import com.ironhack.demobakingapp.model.Accounts.*;
import com.ironhack.demobakingapp.model.Movement;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.model.Users.Admin;
import com.ironhack.demobakingapp.model.Users.ThirdParty;
import com.ironhack.demobakingapp.model.Users.User;
import com.ironhack.demobakingapp.repository.Accounts.AccountRepository;
import com.ironhack.demobakingapp.repository.MovementRepository;
import com.ironhack.demobakingapp.repository.Users.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.Users.ThirdPartyRepository;
import com.ironhack.demobakingapp.repository.Users.UserRepository;
import com.ironhack.demobakingapp.service.impl.FraudConditionsService;
import com.ironhack.demobakingapp.service.impl.Users.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private SavingsService savingsService;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private CheckingService checkingService;

    @Autowired
    private StudentCheckingService studentCheckingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FraudConditionsService fraudConditionsService;

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    /** @Admin method to increment the balance of an account **/
    public void incrementBalance(Long id, BigDecimal amount, String username){

        Account typeOfAccount = accountRepository.findById(id).get();

        User user = userRepository.findByUsername(username).get();

        Admin admin = adminService.findByUsername(user.getUsername());

        if (admin.getUsername().equals(username)) {
            if (typeOfAccount.getClass().equals(Savings.class)){
                Savings savings = savingsService.findById(id);
                savings.getBalance().increaseAmount(amount);
                savingsService.addInterestRate(id);
                savingsService.save(savings);
            } else if (typeOfAccount.getClass().equals(CreditCard.class)){
                CreditCard creditCard = creditCardService.findById(id);
                creditCard.getBalance().increaseAmount(amount);
                creditCardService.addInterestRate(id);
                creditCardService.save(creditCard);
            } else {
                typeOfAccount.getBalance().increaseAmount(amount);
                accountRepository.save(typeOfAccount);
            }
        }
    }

    /** @Admin method to decrement the balance of an account **/
    public void decrementBalance(Long id, BigDecimal amount, String username){

        Account typeOfAccount = accountRepository.findById(id).get();

        User user = userRepository.findByUsername(username).get();

        Admin admin = adminService.findByUsername(user.getUsername());

        if (admin.getUsername().equals(username)) {
            if (typeOfAccount.getClass().equals(Savings.class)){
                Savings savings = savingsService.findById(id);
                savings.getBalance().decreaseAmount(amount);
                savingsService.addInterestRate(id);
                savingsService.save(savings);
            } else if (typeOfAccount.getClass().equals(CreditCard.class)){
                CreditCard creditCard = creditCardService.findById(id);
                creditCard.getBalance().decreaseAmount(amount);
                creditCardService.addInterestRate(id);
                creditCardService.save(creditCard);
            } else {
                typeOfAccount.getBalance().decreaseAmount(amount);
                accountRepository.save(typeOfAccount);
            }
        }
    }

    /** Method to transfer money between accounts **/
    public Movement transfer(MovementDTO movementDTO, String username) {
        AccountHolder user = accountHolderRepository.findByUsername(username).get();

        Account originAccount = accountRepository.findById(movementDTO.getSenderAccount()).get();
        Account destinationAccount = accountRepository.findById(movementDTO.getReceiverAccount()).get();

        /** Exceptions **/

        if(!user.showAccounts().contains(originAccount)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The origin account does not belong to the logged user");
        }
        if(movementDTO.getAmount().compareTo(originAccount.getBalance().getAmount()) > 0 ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "There are not enough funds in the account");
        }
        if(originAccount.isFrozen() || destinationAccount.isFrozen()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your//Their account is FROZEN. So you better let it go ;)");
        }
        if(fraudConditionsService.fraudConditions(movementDTO)){
            switch (originAccount.getClass().getSimpleName().toLowerCase()){
                case "savings":
                    Savings savings = savingsService.findById(movementDTO.getSenderAccount());
                    savings.setStatus(Status.FROZEN);
                    break;
                case "checking":
                    Checking checking = checkingService.findById(movementDTO.getSenderAccount());
                    checking.setStatus(Status.FROZEN);
                    break;
                case "studentchecking":
                    StudentChecking studentChecking = studentCheckingService.findById(movementDTO.getSenderAccount());
                    studentChecking.setStatus(Status.FROZEN);
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not allowed to do this. Your credit card won't be charged.");
            }
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Your account has been blocked because of possible fraud. Please contact us at youre-a-scammer@fake.com");
        }

        Money amount = new Money(movementDTO.getAmount());

        originAccount.getBalance().decreaseAmount(amount);
        accountRepository.save(originAccount);
        destinationAccount.getBalance().increaseAmount(amount);
        accountRepository.save(destinationAccount);
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

    /** Method to transfer with one Third Party **/
    public Movement transferToThirdParty(String name, String hashKey, MovementDTO movementDTO, String username) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Money amount = new Money(movementDTO.getAmount());

        ThirdParty user = thirdPartyRepository.findByName(name);

        Optional<Account> originAccount = accountRepository.findById(movementDTO.getSenderAccount());
        Optional<Account> destinationAccount = accountRepository.findById(movementDTO.getReceiverAccount());

        if (passwordEncoder.matches(hashKey, user.getHashKey())) {
            Movement movement = new Movement();

            /** Transference FROM a Third Party **/
            if (destinationAccount.isPresent()) {
                destinationAccount.get().getBalance().increaseAmount(amount);
                accountRepository.save(destinationAccount.get());
                movement.setQuantity(new Money(movementDTO.getAmount()));
                movement.setConcept(movementDTO.getConcept());
            }

            /** Transference TO a Third Party **/
            if (originAccount.isPresent()) {
                AccountHolder accountHolder = accountHolderRepository.findByUsername(username).get();
                if(accountHolder.showAccounts().contains(originAccount)){
                    originAccount.get().getBalance().decreaseAmount(amount);
                    accountRepository.save(originAccount.get());
                    movement.setQuantity(new Money(movementDTO.getAmount()));
                    movement.setConcept(movementDTO.getConcept());
                }
            }

            /** Exceptions **/
            if (movementDTO.getAmount().compareTo(originAccount.get().getBalance().getAmount()) > 0) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "There are not enough funds in the account");
            }
            if (originAccount.get().isFrozen() || destinationAccount.get().isFrozen()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your//Their account is FROZEN. So you better let it go ;)");
            }
            if (fraudConditionsService.fraudConditions(movementDTO)) {
                switch (originAccount.getClass().getSimpleName().toLowerCase()) {
                    case "savings":
                        Savings savings = savingsService.findById(movementDTO.getSenderAccount());
                        savings.setStatus(Status.FROZEN);
                        break;
                    case "checking":
                        Checking checking = checkingService.findById(movementDTO.getSenderAccount());
                        checking.setStatus(Status.FROZEN);
                        break;
                    case "studentchecking":
                        StudentChecking studentChecking = studentCheckingService.findById(movementDTO.getSenderAccount());
                        studentChecking.setStatus(Status.FROZEN);
                        break;
                    default:
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not allowed to do this. Your credit card won't be charged.");
                }
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Your account has been blocked because of possible fraud. Please contact us at youre-a-scammer@fake.com");
            }
            return movementRepository.save(movement);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your hash key is  not correct. Please try again");
        }
    }


}



