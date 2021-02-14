package com.ironhack.demobakingapp.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demobakingapp.classes.Address;
import com.ironhack.demobakingapp.controller.DTO.Accounts.CreditCardDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.SavingsDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.AccountHolderDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.AddressDTO;
import com.ironhack.demobakingapp.enums.Status;
import com.ironhack.demobakingapp.model.Accounts.Account;
import com.ironhack.demobakingapp.model.Accounts.Savings;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.model.Users.Admin;
import com.ironhack.demobakingapp.repository.Accounts.AccountRepository;
import com.ironhack.demobakingapp.repository.Accounts.CreditCardRepository;
import com.ironhack.demobakingapp.repository.Accounts.SavingsRepository;
import com.ironhack.demobakingapp.repository.Users.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.Users.AdminRepository;
import com.ironhack.demobakingapp.repository.Users.RoleRepository;
import com.ironhack.demobakingapp.service.impl.Accounts.CreditCardService;
import com.ironhack.demobakingapp.service.impl.Accounts.SavingsService;
import com.ironhack.demobakingapp.service.impl.Accounts.StudentCheckingService;
import com.ironhack.demobakingapp.service.impl.Users.AccountHolderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SavingsControllerTest {

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
    AccountRepository accountRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    StudentCheckingService studentCheckingService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<Account> accounts;
    public Admin admin;
    public Admin admin2;
    public Admin admin3;
    public Admin admin4;

    @BeforeEach
    void setUp() {
        AddressDTO address = new AddressDTO("Philadelphia", "Fake Street 123", "Pennsylvania", "USA", "ZP886F");

        admin = new Admin("Morgan Freeman", "2020", "god");
        admin2 = new Admin("Michael Jordan", "2020", "b_god");
        admin3 = new Admin("Mike Tyson", "2020", "d_god");
        admin4 = new Admin("Michael Jackson", "2020", "s_god");

        adminRepository.saveAll(List.of(
                admin,
                admin2,
                admin3,
                admin4
        ));

        /** Account Holders **/

        List<AccountHolderDTO> accountHolderDTOList = new ArrayList<>(List.of(
                new AccountHolderDTO("lola", "lola_93", "123456", LocalDate.of(1993, 12, 7), address
                        , address),
                new AccountHolderDTO("paul", "paul_00", "65Jkp_0", LocalDate.of(2000, 12, 7), address
                        , address),
                new AccountHolderDTO("kareem", "kareem_93", ".!.!.!", LocalDate.of(1993, 12, 7), address
                        , address)
        ));

        AccountHolder lola = accountHolderService.add(accountHolderDTOList.get(0));
        AccountHolder paul = accountHolderService.add(accountHolderDTOList.get(1));
        AccountHolder kareem = accountHolderService.add(accountHolderDTOList.get(2));

        /** Savings **/

        List<SavingsDTO> savingsDTOList = new ArrayList<>(List.of(
                new SavingsDTO(LocalDateTime.now(), lola.getId(), paul.getId(), new BigDecimal("5000.50"), "123456", null, null),
                new SavingsDTO(LocalDateTime.now(), lola.getId(), null, new BigDecimal("5000.50"), "123456", null, null),
                new SavingsDTO(LocalDateTime.now(), paul.getId(), lola.getId(), new BigDecimal("5000.50"), "123456", null, null),
                new SavingsDTO(LocalDateTime.now(), paul.getId(), null, new BigDecimal("5000.50"), "123456", null, null),
                new SavingsDTO(LocalDateTime.now(), kareem.getId(), null, new BigDecimal("5000.50"), "123456", null, null)
        ));

        /** Credit Cards **/

        List<CreditCardDTO> creditCardDTOList = new ArrayList<>(List.of(
                new CreditCardDTO(LocalDateTime.now(), lola.getId(), null, new BigDecimal("5000.50"), null, new BigDecimal(0.3)),
                new CreditCardDTO(LocalDateTime.now(), kareem.getId(), null, new BigDecimal("15000.50"), null, null)
        ));

        /** Accounts List **/
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
    void checkBalance() throws Exception {

        Long savingId = accounts.get(3).getId();

        MvcResult result = mockMvc.perform(
                get("/savings/check-balance/" + savingId)
                        .with(user("paul_00").password("65Jkp_0").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void checkBalance_WrongOwner() throws Exception {

        Long savingId = accounts.get(3).getId();

        MvcResult result = mockMvc.perform(
                get("/savings/check-balance/" + savingId)
                        .with(user("lola_93").password("123456").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isUnauthorized()).andReturn();
    }

    @Test
    void checkBalance_notAccountholder_Error() throws Exception {

        Long savingId = accounts.get(3).getId();

        MvcResult result = mockMvc.perform(
                get("/savings/check-balance/" + savingId)
                        .with(user("god").password("2020").roles("ADMIN")))
                .andExpect(status().isForbidden()).andReturn();
    }

    @Test
    void checkBalanceMinimumBalance_GetPenalty() throws Exception {

        Savings savings = savingsRepository.save(savingsService.transformToSavingsFromDTO(
                new SavingsDTO(LocalDateTime.now(), accountHolderService.findByUsername("lola_93").getId(), null, new BigDecimal("99"), "123456", new BigDecimal(100), null)));

        Long savingId = savings.getId();

        MvcResult result = mockMvc.perform(
                get("/savings/check-balance/" + savingId)
                        .with(user("lola_93").password("123456").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isOk()).andReturn();

        assertTrue((new BigDecimal("59").compareTo(accountRepository.findById(savings.getId()).get().getBalance().getAmount())) == 0);
    }
}