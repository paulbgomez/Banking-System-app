package com.ironhack.demobakingapp.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import com.ironhack.demobakingapp.service.impl.SavingsService;
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
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Address address = new Address("Philadelphia", "Fake Street 123", "Pennsylvania", "USA", "ZP886F");
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO("lola", "lola_93", "123456", LocalDate.of(1993, 12, 07), address
        , address);
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
        SavingsDTO savingsDTO2 = new SavingsDTO( accountHolderRepository.findByName("lola").get().getId(), null, new BigDecimal(10967990.56), "sisterAct", Status.ACTIVE, null, null);

        List<Savings> savingsList = savingsRepository.saveAll(List.of(
                savingsService.transformToSavingsFromDTO(savingsDTO2)
        ));

        String body = objectMapper.writeValueAsString(savingsList);

        Long id = savingsList.get(0).getId();

        MvcResult result = mockMvc.perform(
                get("/savings/balance/" + id)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("lola"));
        assertEquals(new BigDecimal(10967990.56), savingsRepository.findById(id).get().getBalance());
    }
}