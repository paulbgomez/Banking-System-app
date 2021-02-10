package com.ironhack.demobakingapp.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demobakingapp.classes.Address;
import com.ironhack.demobakingapp.controller.DTO.Accounts.AccountHolderDTO;
import com.ironhack.demobakingapp.controller.DTO.Accounts.SavingsDTO;
import com.ironhack.demobakingapp.enums.Status;
import com.ironhack.demobakingapp.model.Accounts.Account;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.model.Accounts.Savings;
import com.ironhack.demobakingapp.model.Users.User;
import com.ironhack.demobakingapp.repository.Accounts.SavingsRepository;
import com.ironhack.demobakingapp.repository.Users.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.Users.RoleRepository;
import com.ironhack.demobakingapp.repository.Users.UserRepository;
import com.ironhack.demobakingapp.service.impl.Users.AccountHolderService;
import com.ironhack.demobakingapp.service.impl.Accounts.SavingsService;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SavingsControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    SavingsService savingsService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AccountHolderService accountHolderService;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    UserRepository userRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    private List<Account> accounts;
    private List<User> users;


    @BeforeEach
    void setUp() {
        Address address = new Address("Philadelphia", "Fake Street 123", "Pennsylvania", "USA", "ZP886F");
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO("lola", "lola_93", "123456", LocalDate.of(1993, 12, 07), address
        , address);
        AccountHolder accountHolder = accountHolderService.create(accountHolderDTO);
        accountHolderRepository.save(accountHolder);

        SavingsDTO savingsDTO2 = new SavingsDTO( accountHolderRepository.findByName("lola").get().getId(), null, new BigDecimal(10967990.56), "sisterAct", Status.ACTIVE, null, null);
        Savings savingX = savingsService.transformToSavingsFromDTO(savingsDTO2);

        accounts = new ArrayList<>();
        accounts.add(savingsRepository.save(savingX));

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @AfterEach
    void tearDown() {
       savingsRepository.deleteAll();
       roleRepository.deleteAll();
       accountHolderRepository.deleteAll();
    }

    @Test
    void add() throws Exception {

        SavingsDTO savingsDTO = new SavingsDTO( accountHolderRepository.findByName("lola").get().getId(), null, new BigDecimal(10967990.56), "sisterAct", Status.ACTIVE, new BigDecimal(100), new BigDecimal(0.3));

        String body = objectMapper.writeValueAsString(savingsDTO);

        MvcResult result = mockMvc.perform(
                post("/savings")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("lola"));
    }

    @Test
    void findAll() throws Exception {

        SavingsDTO savingsDTO2 = new SavingsDTO( accountHolderRepository.findByName("lola").get().getId(), null, new BigDecimal(10967990.56), "sisterAct", Status.ACTIVE, null, null);
        SavingsDTO savingsDTO = new SavingsDTO( accountHolderRepository.findByName("lola").get().getId(), null, new BigDecimal(10967990.56), "sisterAct", Status.ACTIVE, null, null);

        List<Savings> savingsList = savingsRepository.saveAll(List.of(
                savingsService.transformToSavingsFromDTO(savingsDTO),
                savingsService.transformToSavingsFromDTO(savingsDTO2)
        ));

        String body = objectMapper.writeValueAsString(savingsList);

        MvcResult result = mockMvc.perform(
                get("/savings")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("lola"));
        assertEquals(2, savingsRepository.findAll().size());
    }

    @Test
    void checkBalance() throws Exception {
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("lola_93", "123456", AuthorityUtils.createAuthorityList("ACCOUNT_HOLDER"));
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user,null);
        String body = objectMapper.writeValueAsString(accounts.get(0));

        MvcResult result = mockMvc.perform(
                get("/savings/balance/" + accounts.get(0).getId())
                        .principal(testingAuthenticationToken)
                        .content(body)
                        //.with(user(new CustomUserDetails(accountHolderRepository.findByUsername("lola_93").get())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("lola"));
    }

}