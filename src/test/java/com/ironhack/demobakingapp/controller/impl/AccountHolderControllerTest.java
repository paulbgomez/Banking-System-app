package com.ironhack.demobakingapp.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demobakingapp.classes.Address;
import com.ironhack.demobakingapp.controller.DTO.Accounts.AccountHolderDTO;
import com.ironhack.demobakingapp.repository.Users.AccountHolderRepository;
import com.ironhack.demobakingapp.repository.Users.RoleRepository;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountHolderControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private RoleRepository roleRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        //Address address = new Address("Philadelphia", "Fake Street 123", "Pennsylvania", "USA", "ZP886F");
        //accountHolderRepository.save(new AccountHolder("paul", "123456", "paul_93",  LocalDate.of(1993, 12, 07), address, address));
    }

    @AfterEach
    void tearDown() {
       roleRepository.deleteAll();
       accountHolderRepository.deleteAll();
    }

    @Test
    void create() throws Exception {
        Address address = new Address("Chicago", "True Street 123", "Illinois", "USA", "ZHH53");
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO("lola", "lola_93", "123456", LocalDate.of(1993, 12, 07), address
        , address);
        String body = objectMapper.writeValueAsString(accountHolderDTO);
        MvcResult result = mockMvc.perform(
                post("/accountholder")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("lola"));
    }
}