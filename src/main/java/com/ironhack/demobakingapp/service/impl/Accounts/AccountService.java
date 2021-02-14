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
import com.ironhack.demobakingapp.repository.Accounts.CheckingRepository;
import com.ironhack.demobakingapp.repository.Accounts.CreditCardRepository;
import com.ironhack.demobakingapp.repository.Accounts.SavingsRepository;
import com.ironhack.demobakingapp.repository.MovementRepository;
import com.ironhack.demobakingapp.repository.Users.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.Users.ThirdPartyRepository;
import com.ironhack.demobakingapp.repository.Users.UserRepository;
import com.ironhack.demobakingapp.service.impl.FraudConditionsService;
import com.ironhack.demobakingapp.service.impl.Users.AdminService;
import com.ironhack.demobakingapp.service.interfaces.Accounts.*;
import com.ironhack.demobakingapp.service.interfaces.IMovementService;
import com.ironhack.demobakingapp.service.interfaces.Users.IAccountHolderService;
import com.ironhack.demobakingapp.service.interfaces.Users.IAdminService;
import com.ironhack.demobakingapp.service.interfaces.Users.IThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private IAccountHolderService accountHolderService;

    @Autowired
    private IAdminService adminService;

    @Autowired
    private ISavingsService savingsService;

    @Autowired
    private ICreditCardService creditCardService;

    @Autowired
    private IStudentCheckingService studentCheckingService;

    @Autowired
    private ICheckingService checkingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FraudConditionsService fraudConditionsService;

    @Autowired
    private IMovementService movementService;

    @Autowired
    private IThirdPartyService thirdPartyService;

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

        AccountHolder user = accountHolderService.findByUsername(username);

        Account originAccount = accountRepository.findById(movementDTO.getSenderAccount()).get();
        Account destinationAccount = accountRepository.findById(movementDTO.getReceiverAccount()).get();

        /** Exceptions **/

        if(!user.showAccounts().contains(originAccount)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The origin account does not belong to the logged user");
        }
        if(movementDTO.getAmount().compareTo(originAccount.getBalance().getAmount()) > 0 ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "There are not enough funds in the account");
        }
        if (fraudConditionsService.fraudConditionSeconds(movementDTO) || fraudConditionsService.fraudConditionMaxMoney(movementDTO)) {
            originAccount.setFrozen(true);
            originAccount.blockAccount();
            destinationAccount.setFrozen(true);
            destinationAccount.blockAccount();
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your account has been frozen for possible fraud.");
        }
        if(originAccount.isFrozen() || destinationAccount.isFrozen() || originAccount.getStatus().equals(Status.FROZEN) || destinationAccount.getStatus().equals(Status.FROZEN)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Your account is frozen at the moment. You are not allowed to make transferences.");
        }

        Money amount = new Money(movementDTO.getAmount());

        Movement movement = new Movement(originAccount, destinationAccount, amount);

        originAccount.getBalance().decreaseAmount(amount);
        accountRepository.save(originAccount);
        destinationAccount.getBalance().increaseAmount(amount);
        accountRepository.save(destinationAccount);

        return movementService.save(movement);
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
    public Movement transferFromThirdParty(String name, String hashKey, MovementDTO movementDTO) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Money amount = new Money(movementDTO.getAmount());

        ThirdParty user = thirdPartyService.findByName(name);

        Optional<Account> destinationAccount = accountRepository.findById(movementDTO.getReceiverAccount());

        if (passwordEncoder.matches(hashKey, user.getHashKey())) {
            Movement movement = new Movement();

            /** Exceptions **/
            if (destinationAccount.get().isFrozen() || destinationAccount.get().getStatus() == Status.FROZEN) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your//Their account is FROZEN. So you better let it go ;)");
            }
            if (fraudConditionsService.fraudConditionSeconds(movementDTO) || fraudConditionsService.fraudConditionMaxMoney(movementDTO)) {
                    destinationAccount.get().setFrozen(true);
                    destinationAccount.get().blockAccount();
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your//Their account has been frozen for possible fraud.");
            }
            /** Transference FROM a Third Party **/
            destinationAccount.get().getBalance().increaseAmount(amount);
            accountRepository.save(destinationAccount.get());
            movement.setQuantity(new Money(movementDTO.getAmount()));
            movement.setConcept(movementDTO.getConcept());
            return movementService.save(movement);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your hash key is  not correct. Please try again");
        }
    }


    /** Method to transfer with one Third Party **/
    public Movement transferToThirdParty(String name, String hashKey, MovementDTO movementDTO, String username) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Money amount = new Money(movementDTO.getAmount());

        ThirdParty user = thirdPartyService.findByName(name);

        Optional<Account> originAccount = accountRepository.findById(movementDTO.getSenderAccount());

        if (passwordEncoder.matches(hashKey, user.getHashKey())) {
            Movement movement = new Movement();

            /** Exceptions **/
            if (movementDTO.getAmount().compareTo(originAccount.get().getBalance().getAmount()) > 0) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "There are not enough funds in the account");
            }
            if (originAccount.get().isFrozen()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your//Their account is FROZEN. So you better let it go ;)");
            }
            if (fraudConditionsService.fraudConditionSeconds(movementDTO) || fraudConditionsService.fraudConditionMaxMoney(movementDTO)) {
                originAccount.get().setFrozen(true);
                originAccount.get().blockAccount();
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your//Their account has been frozen for possible fraud.");
            }
            /** Transference TO a Third Party **/
            AccountHolder accountHolder = accountHolderService.findByUsername(username);
            if(accountHolder.isOwner(originAccount.get().getId())){
                originAccount.get().getBalance().decreaseAmount(amount);
                accountRepository.save(originAccount.get());
                movement.setQuantity(new Money(movementDTO.getAmount()));
                movement.setConcept(movementDTO.getConcept());
                return movementService.save(movement);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your user is not correct!");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your hash key is  not correct. Please try again");
        }
    }

    /** Check Balance **/

    public BalanceDTO checkBalance(Long id, String username){

        User user = userRepository.findByUsername(username).get();
        AccountHolder accountHolder = accountHolderService.findByUsername(user.getUsername());
        Account account = accountRepository.findById(id).get();
        BalanceDTO balance = new BalanceDTO(id, account.getBalance().getAmount(), account.getBalance().getCurrency());

        if(accountHolder.isOwner(id)){
            if(account instanceof Savings){
                savingsService.addInterestRate(id);
                Savings savings = savingsService.findById(id);
                if ((savings.getMinimumBalance().getAmount().compareTo(savings.getBalance().getAmount()) == 1) && !savings.isBelowMinimumBalance()){
                    savings.setBelowMinimumBalance(true);
                    savings.getBalance().decreaseAmount(savings.getPenalty());
                }
                savingsRepository.save(savings);
            }
            if(account instanceof CreditCard){
                creditCardService.addInterestRate(id);
                creditCardRepository.save((CreditCard) account);
            }
            if(account instanceof Checking){
                checkingService.applyMonthlyFee(id);
                Checking checking = checkingService.findById(id);
                if ((checking.getMinimumBalance().getAmount().compareTo(checking.getBalance().getAmount()) == 1) && !checking.isBelowMinimumBalance()){
                    checking.setBelowMinimumBalance(true);
                    checking.getBalance().decreaseAmount(checking.getPenalty());
                }
                checkingRepository.save(checking);
            }
            return balance;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User does not have saving accounts");
        }
    }

}



