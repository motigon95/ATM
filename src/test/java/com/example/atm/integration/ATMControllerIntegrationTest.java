package com.example.atm.integration;

import com.example.atm.dto.UserDTO;
import com.example.atm.respository.IAccountRepository;
import com.example.atm.service.IAccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ATMControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    IAccountService accountService;

    @Autowired
    IAccountRepository accountRepository;

    @Test
    public void testShouldLoginUserSuccessfully() throws Exception {
        UserDTO userDTO = new UserDTO("antonio.gom", "1234");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();

        String requestJson = ow.writeValueAsString(userDTO);
        mockMvc.perform(get("/atm/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testShouldReturnNotFoundWhenLoginUnknownUser() throws Exception {
        UserDTO userDTO = new UserDTO("test", "test");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();

        String requestJson = ow.writeValueAsString(userDTO);
        mockMvc.perform(get("/atm/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

   /* @Test
    public void testShouldBeLoginBeforeGettingBalance() throws Exception {

        mockMvc.perform(get("/atm/balance"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }*/

    @Test
    public void testShouldTReturnBalanceWhenUserIsLogin() throws Exception {

        UserDTO userDTO = new UserDTO("antonio.gom", "1234");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();

        String requestJson = ow.writeValueAsString(userDTO);
        mockMvc.perform(get("/atm/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                )
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/atm/balance"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
