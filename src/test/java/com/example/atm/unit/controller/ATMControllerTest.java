package com.example.atm.unit.controller;

import com.example.atm.controller.ATMController;
import com.example.atm.dto.AccountBalanceResponseDTO;
import com.example.atm.dto.UserDTO;
import com.example.atm.exceptions.UserNotFoundException;
import com.example.atm.service.IAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ATMControllerTest {

    @Mock
    private IAccountService accountService;

    @InjectMocks
    private ATMController atmController;

    @Test
    void testLogginUserSuccessfully() {

        //arrange
        UserDTO userDTO = new UserDTO("antonio.gom", "1234");
        when(accountService.loginUser(userDTO)).thenReturn(ResponseEntity.ok().build());
        //act
        ResponseEntity<?> currentResponse = atmController.loginUser(userDTO);
        //assert
        verify((accountService), atLeastOnce()).loginUser(userDTO);
        assertEquals(HttpStatus.OK, currentResponse.getStatusCode());
    }

    @Test
    void testLogginThrowsUserNotFoundException() {

        //arrange
        UserDTO userDTO = new UserDTO("user-test", "user-test");
        when(accountService.loginUser(userDTO)).thenThrow(new UserNotFoundException(userDTO.getUserName()));
        //act&assert
        assertThrows(UserNotFoundException.class, () -> {
            atmController.loginUser(userDTO);
        });
        verify((accountService), atLeastOnce()).loginUser(userDTO);
    }

    @Test
    void testShouldReturnBalanceSuccessfully() {

        //arrange
        ResponseEntity<AccountBalanceResponseDTO> expectedResponse = new ResponseEntity<>(new AccountBalanceResponseDTO("test", 100.00), HttpStatus.OK);
        when(accountService.getBalance()).thenReturn(expectedResponse);
        //act
        ResponseEntity<?> currentResponse = atmController.getBalance();
        //assert
        verify((accountService), atLeastOnce()).getBalance();
        assertEquals(HttpStatus.OK, currentResponse.getStatusCode());
    }

}
