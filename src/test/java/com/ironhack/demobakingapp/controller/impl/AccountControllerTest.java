package com.ironhack.demobakingapp.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demobakingapp.classes.Address;
import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.controller.DTO.Accounts.CreditCardDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.SavingsDTO;
import com.ironhack.demobakingapp.controller.DTO.Transferences.MovementDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.AccountHolderDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.AddressDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.ThirdPartyDTO;
import com.ironhack.demobakingapp.enums.Status;
import com.ironhack.demobakingapp.enums.UserRole;
import com.ironhack.demobakingapp.model.Accounts.Account;
import com.ironhack.demobakingapp.model.Accounts.Savings;
import com.ironhack.demobakingapp.model.Movement;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.model.Users.Admin;
import com.ironhack.demobakingapp.model.Users.Role;
import com.ironhack.demobakingapp.model.Users.ThirdParty;
import com.ironhack.demobakingapp.repository.Accounts.AccountRepository;
import com.ironhack.demobakingapp.repository.Accounts.CreditCardRepository;
import com.ironhack.demobakingapp.repository.Accounts.SavingsRepository;
import com.ironhack.demobakingapp.repository.MovementRepository;
import com.ironhack.demobakingapp.repository.Users.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.Users.AdminRepository;
import com.ironhack.demobakingapp.repository.Users.RoleRepository;
import com.ironhack.demobakingapp.repository.Users.ThirdPartyRepository;
import com.ironhack.demobakingapp.service.impl.Accounts.AccountService;
import com.ironhack.demobakingapp.service.impl.Accounts.CreditCardService;
import com.ironhack.demobakingapp.service.impl.Accounts.SavingsService;
import com.ironhack.demobakingapp.service.impl.Users.AccountHolderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    SavingsService savingsService;

    @Autowired
    CreditCardService creditCardService;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AccountHolderService accountHolderService;

    @Autowired
    MovementRepository movementRepository;

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    RoleRepository roleRepository;

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
                new SavingsDTO(LocalDateTime.now(), kareem.getId(), null, new BigDecimal("5000.50"), "123456", null, null),
                new SavingsDTO(LocalDateTime.now(), kareem.getId(), null, new BigDecimal("5000.50"), "frozen", null, null)
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

        /** Transferences **/

        movementRepository.save(new Movement(accounts.get(3), accounts.get(6), new Money(new BigDecimal("10"))));
        movementRepository.save(new Movement(accounts.get(3), accounts.get(4), new Money(new BigDecimal("10"))));

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @AfterEach
    void tearDown() {
        movementRepository.deleteAll();
        savingsRepository.deleteAll();
        creditCardRepository.deleteAll();
        roleRepository.deleteAll();
        accountHolderRepository.deleteAll();
        adminRepository.deleteAll();
        thirdPartyRepository.deleteAll();
    }

    @Test
    void newTransfer_LocalMovementTwoDifferentAccounts_MovementOK() throws Exception {

        Long savingsId = accounts.get(0).getId();
        Long savingsId2 = accounts.get(4).getId();

        MovementDTO movementDTO = new MovementDTO(savingsId, "Kareem", savingsId2, new BigDecimal("1000.50"));

        String body = objectMapper.writeValueAsString(movementDTO);

        MvcResult result = mockMvc.perform(
                post("/new-transference/")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("lola_93").password("123456").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isCreated()).andReturn();

        assertTrue((new BigDecimal("4000.00").compareTo(accountRepository.findById(savingsId).get().getBalance().getAmount())) == 0);
    }

    @Test
    void newTransferSecondaryOwner_LocalMovementTwoDifferentAccounts_MovementOK() throws Exception {

        Long savingsId = accounts.get(0).getId();
        Long savingsId2 = accounts.get(4).getId();

        MovementDTO movementDTO = new MovementDTO(savingsId, "Kareem", savingsId2, new BigDecimal(1000.50));

        String body = objectMapper.writeValueAsString(movementDTO);

        MvcResult result = mockMvc.perform(
                post("/new-transference/")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("paul_00").password("65Jkp_0").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isCreated()).andReturn();

        assertTrue((new BigDecimal(4000.00).compareTo(accountRepository.findById(savingsId).get().getBalance().getAmount())) == 0);
    }

    @Test
    void newTransferNotOwner_LocalMovementTwoDifferentAccounts_Error() throws Exception {

        Long savingsId = accounts.get(0).getId();
        Long savingsId2 = accounts.get(4).getId();

        MovementDTO movementDTO = new MovementDTO(savingsId, "Kareem", savingsId2, new BigDecimal(1000.50));

        String body = objectMapper.writeValueAsString(movementDTO);

        MvcResult result = mockMvc.perform(
                post("/new-transference/")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("kareem_93").password(".!.!.!").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isUnauthorized()).andReturn();

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            accountService.transfer(movementDTO, accounts.get(0).getPrimaryOwner().getUsername());
        });
    }

    @Test
    void newTransferNotEnoughFunds_LocalMovementTwoDifferentAccounts_Error() throws Exception {

        Long savingsId = accounts.get(0).getId();
        Long savingsId2 = accounts.get(4).getId();

        MovementDTO movementDTO = new MovementDTO(savingsId, "Kareem", savingsId2, new BigDecimal(1000000.50));

        String body = objectMapper.writeValueAsString(movementDTO);

        MvcResult result = mockMvc.perform(
                post("/new-transference/")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("lola_93").password("123456").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isNotAcceptable()).andReturn();

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            accountService.transfer(movementDTO, accounts.get(0).getPrimaryOwner().getUsername());
        });
    }

    @Test
    void newTransferToFrozen_LocalMovementTwoDifferentAccounts_Error() throws Exception {

        Long savingsId = accounts.get(0).getId();
        Long savingsId2 = accounts.get(5).getId();
        accounts.get(5).setFrozen(true);
        accounts.get(5).blockAccount();
        accountRepository.save(accounts.get(5));

        MovementDTO movementDTO = new MovementDTO(savingsId, "Kareem", savingsId2, new BigDecimal(10.50));

        String body = objectMapper.writeValueAsString(movementDTO);

        MvcResult result = mockMvc.perform(
                post("/new-transference/")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("lola_93").password("123456").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isNotAcceptable()).andReturn();

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            accountService.transfer(movementDTO, accounts.get(0).getPrimaryOwner().getUsername());
        });
    }

    @Test
    void newTransferFromFrozen_LocalMovementTwoDifferentAccounts_Error() throws Exception {

        Long savingsId = accounts.get(0).getId();
        accounts.get(0).setFrozen(true);
        accounts.get(0).blockAccount();
        accountRepository.save(accounts.get(0));
        Long savingsId2 = accounts.get(5).getId();

        MovementDTO movementDTO = new MovementDTO(savingsId, "Kareem", savingsId2, new BigDecimal(10.50));

        String body = objectMapper.writeValueAsString(movementDTO);

        MvcResult result = mockMvc.perform(
                post("/new-transference/")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("lola_93").password("123456").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isNotAcceptable()).andReturn();

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            accountService.transfer(movementDTO, accounts.get(0).getPrimaryOwner().getUsername());
        });
    }

    @Test
    void newTransferFraudAccount_LocalMovementTwoDifferentAccounts_Error() throws Exception {

        Long savingsId = accounts.get(3).getId();
        Long savingsId2 = accounts.get(5).getId();

        MovementDTO movementDTO = new MovementDTO(savingsId, "Kareem", savingsId2, new BigDecimal(10.50));

        String body = objectMapper.writeValueAsString(movementDTO);

        MvcResult result = mockMvc.perform(
                post("/new-transference/")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("lola_93").password("123456").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isUnauthorized()).andReturn();

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            accountService.transfer(movementDTO, accounts.get(0).getPrimaryOwner().getUsername());
        });
    }

    @Test
    void newTransferToThirdParty_LocalMovementTwoDifferentAccounts_Ok() throws Exception {

        ThirdPartyDTO thirdPartyDTO = new ThirdPartyDTO("random", "123456");

        ThirdParty random = thirdPartyRepository.save(
                new ThirdParty(
                        thirdPartyDTO.getName(),
                        thirdPartyDTO.getHashKey()
                ));

        Account account = new Account(new Money(new BigDecimal(1000.50)), accountHolderRepository.findByUsername("lola_93").get());
        accountRepository.save(account);

        MovementDTO movementDTO = new MovementDTO(accountRepository.findById(account.getId()).get().getId(), "random", "transfer to third party", new BigDecimal(1000));
        String body = objectMapper.writeValueAsString(movementDTO);

        MvcResult result = mockMvc.perform(
                post("/new-transference/to/" + random.getName() + "/123456")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("lola_93").password("123456").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isCreated()).andReturn();

        assertTrue((new BigDecimal(0.50).compareTo(accountRepository.findById(account.getId()).get().getBalance().getAmount())) == 0);
    }

    @Test
    void newTransferFromThirdParty_LocalMovementTwoDifferentAccounts_Ok() throws Exception {

        ThirdPartyDTO thirdPartyDTO = new ThirdPartyDTO("random", "123456");

        ThirdParty random = thirdPartyRepository.save(
                new ThirdParty(
                        thirdPartyDTO.getName(),
                        thirdPartyDTO.getHashKey()
                ));

        Account account = new Account(new Money(new BigDecimal(1000)), accountHolderRepository.findByUsername("lola_93").get());
        accountRepository.save(account);

        MovementDTO movementDTO = new MovementDTO("random", "transfer to third party",accountRepository.findById(account.getId()).get().getId(), new BigDecimal(1000));
        String body = objectMapper.writeValueAsString(movementDTO);

        MvcResult result = mockMvc.perform(
                post("/new-transference/from/" + random.getName() + "/123456")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("lola_93").password("123456").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isCreated()).andReturn();

        assertTrue((new BigDecimal(2000).compareTo(accountRepository.findById(account.getId()).get().getBalance().getAmount())) == 0);

    }

    @Test
    void newTransferToThirdParty_LocalMovementNotOwner_Error() throws Exception {

        ThirdPartyDTO thirdPartyDTO = new ThirdPartyDTO("random", "123456");

        ThirdParty random = thirdPartyRepository.save(
                new ThirdParty(
                        thirdPartyDTO.getName(),
                        thirdPartyDTO.getHashKey()
                ));

        Account account = new Account(new Money(new BigDecimal(1000.50)), accountHolderRepository.findByUsername("lola_93").get());
        accountRepository.save(account);

        MovementDTO movementDTO = new MovementDTO(accountRepository.findById(account.getId()).get().getId(), "random", "transfer to third party", new BigDecimal(1000));
        String body = objectMapper.writeValueAsString(movementDTO);

        MvcResult result = mockMvc.perform(
                post("/new-transference/to/" + random.getName() + "/123456")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("kareem_93").password(".!.!.!").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isUnauthorized()).andReturn();
    }

    @Test
    void newTransferToThirdParty_LocalMovementNotEnoughFunds_Error() throws Exception {

        ThirdPartyDTO thirdPartyDTO = new ThirdPartyDTO("random", "123456");

        ThirdParty random = thirdPartyRepository.save(
                new ThirdParty(
                        thirdPartyDTO.getName(),
                        thirdPartyDTO.getHashKey()
                ));

        Account account = new Account(new Money(new BigDecimal(1000.50)), accountHolderRepository.findByUsername("lola_93").get());
        accountRepository.save(account);

        MovementDTO movementDTO = new MovementDTO(accountRepository.findById(account.getId()).get().getId(), "random", "transfer to third party", new BigDecimal(10000000));
        String body = objectMapper.writeValueAsString(movementDTO);

        MvcResult result = mockMvc.perform(
                post("/new-transference/to/" + random.getName() + "/123456")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("lola_93").password("123456").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isNotAcceptable()).andReturn();
    }

    @Test
    void newTransferToThirdParty_LocalMovementFrozen_Error() throws Exception {

        ThirdPartyDTO thirdPartyDTO = new ThirdPartyDTO("random", "123456");

        ThirdParty random = thirdPartyRepository.save(
                new ThirdParty(
                        thirdPartyDTO.getName(),
                        thirdPartyDTO.getHashKey()
                ));

        Account account = new Account(new Money(new BigDecimal("1000.50")), accountHolderRepository.findByUsername("lola_93").get());
        account.setFrozen(true);
        accountRepository.save(account);

        MovementDTO movementDTO = new MovementDTO(accountRepository.findById(account.getId()).get().getId(), "random", "transfer to third party", new BigDecimal(10));
        String body = objectMapper.writeValueAsString(movementDTO);

        MvcResult result = mockMvc.perform(
                post("/new-transference/to/" + random.getName() + "/123456")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("lola_93").password("123456").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isUnauthorized()).andReturn();
    }

    @Test
    void newTransferToThirdParty_BadHashKey_Error() throws Exception {

        ThirdPartyDTO thirdPartyDTO = new ThirdPartyDTO("random", "123456");

        ThirdParty random = thirdPartyRepository.save(
                new ThirdParty(
                        thirdPartyDTO.getName(),
                        thirdPartyDTO.getHashKey()
                ));

        Account account = new Account(new Money(new BigDecimal("1000.50")), accountHolderRepository.findByUsername("lola_93").get());
        account.setFrozen(true);
        accountRepository.save(account);

        MovementDTO movementDTO = new MovementDTO(accountRepository.findById(account.getId()).get().getId(), "random", "transfer to third party", new BigDecimal(10));
        String body = objectMapper.writeValueAsString(movementDTO);

        MvcResult result = mockMvc.perform(
                post("/new-transference/to/" + random.getName() + "/123")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("lola_93").password("123456").roles("ACCOUNT_HOLDER")))
                .andExpect(status().isUnauthorized()).andReturn();
    }

}