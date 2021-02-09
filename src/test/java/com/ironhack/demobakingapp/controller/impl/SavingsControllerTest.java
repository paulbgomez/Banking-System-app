package com.ironhack.demobakingapp.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demobakingapp.classes.Address;
import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.controller.DTO.AccountHolderDTO;
import com.ironhack.demobakingapp.controller.DTO.SavingsDTO;
import com.ironhack.demobakingapp.enums.Status;
import com.ironhack.demobakingapp.model.AccountHolder;
import com.ironhack.demobakingapp.model.Savings;
import com.ironhack.demobakingapp.repository.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.AccountRepository;
import com.ironhack.demobakingapp.repository.RoleRepository;
import com.ironhack.demobakingapp.repository.SavingsRepository;
import com.ironhack.demobakingapp.service.impl.AccountHolderService;
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
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SavingsControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AccountHolderService accountHolderService;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Address address = new Address("Philadelphia", "Fake Street 123", "Pennsylvania", "USA", "ZP886F");
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO(LocalDate.of(1993, 12, 07), "lola", "lola_93", address, address, "123456");
        AccountHolder accountHolder = accountHolderService.create(accountHolderDTO);
        accountHolderRepository.save(accountHolder);
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
}