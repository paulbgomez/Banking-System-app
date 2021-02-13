package com.ironhack.demobakingapp.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demobakingapp.classes.Address;
import com.ironhack.demobakingapp.controller.DTO.Accounts.CheckingDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.CreditCardDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.SavingsDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.AccountHolderDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.AdminDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.ThirdPartyDTO;
import com.ironhack.demobakingapp.enums.Status;
import com.ironhack.demobakingapp.model.Accounts.*;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    AccountRepository accountRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    StudentCheckingService studentCheckingService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    private List<Account> accounts;
    public Admin admin;
    public Admin admin2;
    public Admin admin3;
    public Admin admin4;

    @BeforeEach
    void setUp() {
        Address address = new Address("Philadelphia", "Fake Street 123", "Pennsylvania", "USA", "ZP886F");

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

        /** Savings **/
        List<SavingsDTO> savingsDTOList = new ArrayList<>();

        savingsDTOList.addAll(List.of(
                new SavingsDTO(LocalDateTime.now(), lola.getId(), paul.getId(), new BigDecimal(5000.50), "123456", Status.ACTIVE, null, null),
                new SavingsDTO(LocalDateTime.now(), lola.getId(), null, new BigDecimal(5000.50), "123456", Status.ACTIVE, null, null),
                new SavingsDTO(LocalDateTime.now(), paul.getId(), lola.getId(), new BigDecimal(5000.50), "123456", Status.ACTIVE, null, null),
                new SavingsDTO(LocalDateTime.now(), paul.getId(), null, new BigDecimal(5000.50), "123456", Status.ACTIVE, null, null),
                new SavingsDTO(LocalDateTime.now(), kareem.getId(), null, new BigDecimal(5000.50), "123456", Status.ACTIVE, null, null)
        ));

        /** Credit Cards **/
        List<CreditCardDTO> creditCardDTOList = new ArrayList<>();

        creditCardDTOList.addAll(List.of(
                new CreditCardDTO(LocalDateTime.now(), lola.getId(), null, new BigDecimal(5000.50),  null, new BigDecimal(0.3)),
                new CreditCardDTO(LocalDateTime.now(), kareem.getId(), null, new BigDecimal(15000.50), null, null)
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
    void checkBalanceAll_AdminLog_Ok() throws Exception {

        Long adminId = admin.getId();

        String body = objectMapper.writeValueAsString(savingsRepository.findAll());

        MvcResult result = mockMvc.perform(
                get("/admin/account-balance/all/" + adminId)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("god").password("2020").roles("ADMIN")))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void checkBalanceAll_NotAdminLog_Error() throws Exception {

        Long adminId = admin.getId();

        String body = objectMapper.writeValueAsString(savingsRepository.findAll());

        MvcResult result = mockMvc.perform(
                get("/admin/account-balance/all/" + adminId)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("lola").password("123456").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isForbidden()).andReturn();
    }

    @Test
    void findAllAdmins_AdminLog_Ok() throws Exception {

        String body = objectMapper.writeValueAsString(adminRepository.findAll());

        MvcResult result = mockMvc.perform(
                get("/admin/info")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("god").password("2020").roles("ADMIN")))
                .andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Mike Tyson"));
    }

    @Test
    void findAllAdmins_NotAdminLog_Error() throws Exception {

        String body = objectMapper.writeValueAsString(adminRepository.findAll());

        MvcResult result = mockMvc.perform(
                get("/admin/info")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("paul_00").password("65Jkp_0").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isForbidden()).andReturn();
    }

    @Test
    void findAllSavingAccounts_AdminLog_Ok() throws Exception {

        String body = objectMapper.writeValueAsString(savingsRepository.findAll());

        MvcResult result = mockMvc.perform(
                get("/admin/savings")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("god").password("2020").roles("ADMIN")))
                .andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("lola"));
    }

    @Test
    void findAllSavingAccounts_NotAdminLog_Error() throws Exception {

        String body = objectMapper.writeValueAsString(savingsRepository.findAll());

        MvcResult result = mockMvc.perform(
                get("/admin/savings")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("paul_00").password("65Jkp_0").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isForbidden()).andReturn();
    }

    @Test
    void checkBalanceAdmin_AdminLog_Ok() throws Exception {
        Long accountID = accounts.get(0).getId();
        String body = objectMapper.writeValueAsString(savingsRepository.findById(accountID));

        MvcResult result = mockMvc.perform(
                get("/admin/savings/check-balance/" + accountID)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("god").password("2020").roles("ADMIN")))
                .andExpect(status().isOk()).andReturn();

    }

    @Test
    void checkBalanceAdmin_NotAdminLog_Error() throws Exception {
        
        Long accountID = accounts.get(1).getId();

        String body = objectMapper.writeValueAsString(savingsRepository.findById(accountID));

        MvcResult result = mockMvc.perform(
                get("/admin/savings/check-balance/" + accountID)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("fake").password("random").roles("ADMIN")))
                .andExpect(status().isUnauthorized()).andReturn();

    }

    @Test
    void addSavingsAccount() throws Exception {
        Address address = new Address("Philadelphia", "Fake Street 123", "Pennsylvania", "USA", "ZP886F");

        AccountHolder tom = accountHolderService.add(
                new AccountHolderDTO("Tom Brady", "lotr", "7ringsLikeAriana", LocalDate.of(1977, 12, 07), address
                , address
                ));

        SavingsDTO savings = new SavingsDTO(LocalDateTime.now(), tom.getId(), null, new BigDecimal(10000.50), "xxxx", Status.ACTIVE, null, null);

        String body = objectMapper.writeValueAsString(savings);

        Long adminId = admin.getId();

        MvcResult result = mockMvc.perform(
                post("/admin/savings/" + adminId)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("god").password("2020").roles("ADMIN")))
                .andExpect(status().isCreated()).andReturn();


    }

    @Test
    void addCheckingAccount() throws Exception {
        Address address = new Address("Philadelphia", "Fake Street 123", "Pennsylvania", "USA", "ZP886F");

        AccountHolder tom = accountHolderService.add(
                new AccountHolderDTO("Tom Brady", "lotr", "7ringsLikeAriana", LocalDate.of(1977, 12, 07), address
                        , address
                ));

        CheckingDTO checkingDTO = new CheckingDTO(LocalDateTime.now(), tom.getId(), null, new BigDecimal(1000.50), "xxxx", Status.ACTIVE);

        String body = objectMapper.writeValueAsString(checkingDTO);

        Long adminId = admin.getId();

        MvcResult result = mockMvc.perform(
                post("/admin/checkings/" + adminId)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("god").password("2020").roles("ADMIN")))
                .andExpect(status().isCreated()).andReturn();

    }

    @Test
    void addCheckingAccountYoungPerson() throws Exception {
        Address address = new Address("Philadelphia", "Fake Street 123", "Pennsylvania", "USA", "ZP886F");

        AccountHolder tom = accountHolderService.add(
                new AccountHolderDTO("Tom Brady", "lotr", "7ringsLikeAriana", LocalDate.of(2000, 12, 07), address
                        , address
                ));

        CheckingDTO checkingDTO = new CheckingDTO(LocalDateTime.now(), tom.getId(), null, new BigDecimal(10000.50), "xxxx", Status.ACTIVE);


        String body = objectMapper.writeValueAsString(checkingDTO);

        Long adminId = admin.getId();

        MvcResult result = mockMvc.perform(
                post("/admin/checkings/" + adminId)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("god").password("2020").roles("ADMIN")))
                .andExpect(status().isCreated()).andReturn();

    }

    @Test
    void addCreditCardAccount() throws Exception {
        Address address = new Address("Philadelphia", "Fake Street 123", "Pennsylvania", "USA", "ZP886F");

        AccountHolder tom = accountHolderService.add(
                new AccountHolderDTO("Tom Brady", "lotr", "7ringsLikeAriana", LocalDate.of(2000, 12, 07), address
                        , address
                ));

        CreditCardDTO creditCardDTO = new CreditCardDTO(LocalDateTime.now(), tom.getId(), null, new BigDecimal(1000000.50), null, null);

        String body = objectMapper.writeValueAsString(creditCardDTO);

        Long adminId = admin.getId();

        MvcResult result = mockMvc.perform(
                post("/admin/credit-card/" + adminId)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("god").password("2020").roles("ADMIN")))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    void addAdmin() throws Exception {
        AdminDTO adminRandom = new AdminDTO("random", "random", "random");

        String body = objectMapper.writeValueAsString(adminRandom);

        Long adminId = admin.getId();

        MvcResult result = mockMvc.perform(
                post("/new/admin/" + adminId)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("god").password("2020").roles("ADMIN")))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    void addAccountHolder() throws Exception {
        Address address = new Address("Philadelphia", "Fake Street 123", "Pennsylvania", "USA", "ZP886F");

        AccountHolderDTO tom = new AccountHolderDTO("Tom Brady", "lotr", "7ringsLikeAriana", LocalDate.of(2000, 12, 07), address, address);

        String body = objectMapper.writeValueAsString(tom);

        Long adminId = admin.getId();

        MvcResult result = mockMvc.perform(
                post("/new/account-holder/" + adminId)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("god").password("2020").roles("ADMIN")))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    void addThirdParty() throws Exception {
        ThirdPartyDTO thirdPartyDTO = new ThirdPartyDTO("Tom Brady", "7ringsLikeAriana");

        String body = objectMapper.writeValueAsString(thirdPartyDTO);

        Long adminId = admin.getId();

        MvcResult result = mockMvc.perform(
                post("/new/third-party/" + adminId)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("god").password("2020").roles("ADMIN")))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    void incrementBalance() throws Exception {

        Long accountId = accounts.get(0).getId();

        String body = objectMapper.writeValueAsString(accounts.get(0));

        MvcResult result = mockMvc.perform(
                put("/admin/account/increment/" + accountId + "/" + new BigDecimal(1000).toPlainString())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("god").password("2020").roles("ADMIN")))
                .andExpect(status().isNoContent()).andReturn();

        assertTrue((new BigDecimal(6000.50).compareTo(accountRepository.findById(accountId).get().getBalance().getAmount())) == 0);
    }

    @Test
    void decrementBalance() throws Exception {
        Long accountId = accounts.get(0).getId();

        String body = objectMapper.writeValueAsString(accounts.get(0));

        MvcResult result = mockMvc.perform(
                put("/admin/account/decrement/" + accountId + "/" + new BigDecimal(1000).toPlainString())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("god").password("2020").roles("ADMIN")))
                .andExpect(status().isNoContent()).andReturn();

        assertTrue((new BigDecimal(4000.50).compareTo(accountRepository.findById(accountId).get().getBalance().getAmount())) == 0);
    }
}