package com.ironhack.demobakingapp.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demobakingapp.classes.Address;
import com.ironhack.demobakingapp.controller.DTO.Accounts.CreditCardDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.SavingsDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.AccountHolderDTO;
import com.ironhack.demobakingapp.enums.Status;
import com.ironhack.demobakingapp.model.Accounts.Account;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.model.Users.Admin;
import com.ironhack.demobakingapp.repository.Accounts.CreditCardRepository;
import com.ironhack.demobakingapp.repository.Accounts.SavingsRepository;
import com.ironhack.demobakingapp.repository.Users.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.Users.AdminRepository;
import com.ironhack.demobakingapp.repository.Users.RoleRepository;
import com.ironhack.demobakingapp.service.impl.Accounts.CreditCardService;
import com.ironhack.demobakingapp.service.impl.Accounts.SavingsService;
import com.ironhack.demobakingapp.service.impl.Users.AccountHolderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
class AdminControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    SavingsService savingsService;

    @Autowired
    CreditCardService creditCardService;

    @Autowired
    AccountHolderService accountHolderService;

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    private List<Account> accounts;

    @BeforeEach
    void setUp() {
        Address address = new Address("Philadelphia", "Fake Street 123", "Pennsylvania", "USA", "ZP886F");

        Admin admin = new Admin("Morgan Freeman", "2020", "god");

        adminRepository.save(admin);

        List<AccountHolderDTO> accountHolderDTOList = new ArrayList<>();

        accountHolderDTOList.addAll(List.of(
                new AccountHolderDTO("lola", "lola_93", "123456", LocalDate.of(1993, 12, 07), address
                        , address),
                new AccountHolderDTO("paul", "paul_00", "65Jkp_0", LocalDate.of(2000, 12, 07), address
                        , address),
                new AccountHolderDTO("kareem", "kareem_93", ".!.!.!", LocalDate.of(1993, 12, 07), address
                        , address)
        ));

        AccountHolder lola = accountHolderService.add(accountHolderDTOList.get(0));
        AccountHolder paul = accountHolderService.add(accountHolderDTOList.get(1));
        AccountHolder kareem = accountHolderService.add(accountHolderDTOList.get(2));

        List<SavingsDTO> savingsDTOList = new ArrayList<>();

        savingsDTOList.addAll(List.of(
                new SavingsDTO(LocalDateTime.now(), lola.getId(), paul.getId(), new BigDecimal(5000.50), "123456", Status.ACTIVE, null, null),
                new SavingsDTO(LocalDateTime.now(), lola.getId(), null, new BigDecimal(5000.50), "123456", Status.ACTIVE, null, null),
                new SavingsDTO(LocalDateTime.now(), paul.getId(), lola.getId(), new BigDecimal(5000.50), "123456", Status.ACTIVE, null, null),
                new SavingsDTO(LocalDateTime.now(), paul.getId(), null, new BigDecimal(5000.50), "123456", Status.ACTIVE, null, null),
                new SavingsDTO(LocalDateTime.now(), kareem.getId(), null, new BigDecimal(5000.50), "123456", Status.ACTIVE, null, null)
        ));

        List<CreditCardDTO> creditCardDTOList = new ArrayList<>();

        creditCardDTOList.addAll(List.of(
                new CreditCardDTO(LocalDateTime.now(), lola.getId(), null, new BigDecimal(5000.50),  null, new BigDecimal(0.3)),
                new CreditCardDTO(LocalDateTime.now(), kareem.getId(), null, new BigDecimal(5000.50), null, null)
        ));

        accounts = new ArrayList<>();
        accounts.addAll(List.of(
                savingsService.save(savingsService.transformToSavingsFromDTO(savingsDTOList.get(0))),
                savingsService.save(savingsService.transformToSavingsFromDTO(savingsDTOList.get(1))),
                savingsService.save(savingsService.transformToSavingsFromDTO(savingsDTOList.get(2))),
                savingsService.save(savingsService.transformToSavingsFromDTO(savingsDTOList.get(3))),
                savingsService.save(savingsService.transformToSavingsFromDTO(savingsDTOList.get(4))),
                creditCardService.save(creditCardService.transformToCreditCardFromDTO(creditCardDTOList.get(0))),
                creditCardService.save(creditCardService.transformToCreditCardFromDTO(creditCardDTOList.get(1)))
        ));

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @AfterEach
    void tearDown() {
        savingsRepository.deleteAll();
        creditCardRepository.deleteAll();
        roleRepository.deleteAll();
        adminRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void checkBalanceAll() {
    }

    @Test
    void findAllAdmins() {
    }

    @Test
    void findAllSavingAccounts() {
    }

    @Test
    void checkBalanceAdmin() {
    }

    @Test
    void addSavingsAccount() {
    }

    @Test
    void addCheckingAccount() {
    }

    @Test
    void addCreditCardAccount() {
    }

    @Test
    void addAdmin() {
    }

    @Test
    void ass() {
    }

    @Test
    void addAccountHolder() {
    }

    @Test
    void addThirdParty() {
    }

    @Test
    void incrementBalance() {
    }

    @Test
    void decrementBalance() {
    }
}