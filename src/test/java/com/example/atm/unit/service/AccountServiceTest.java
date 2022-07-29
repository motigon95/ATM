package com.example.atm.unit.service;

import com.example.atm.dto.AccountBalanceResponseDTO;
import com.example.atm.dto.UserDTO;
import com.example.atm.exceptions.UserNotFoundException;
import com.example.atm.model.Account;
import com.example.atm.model.User;
import com.example.atm.respository.IAccountRepository;
import com.example.atm.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private IAccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void testLogginUserSuccessfully(){
        //arrange
        UserDTO userDTO = new UserDTO("antonio.gom", "1234");
        User user = new User("Antonio Gomez Sar", "antonio.gom", "1234");
        when(accountRepository.getUser(userDTO)).thenReturn(user);
        //act
        ResponseEntity<?> currentResponse = accountService.loginUser(userDTO);
        //assert
        verify((accountRepository), atLeastOnce()).getUser(userDTO);
        assertEquals(HttpStatus.OK, currentResponse.getStatusCode());
    }

    @Test
    public void testLogginThrowsUserNotFoundException(){
        //arrange
        UserDTO userDTO = new UserDTO("antonio.gom", "1234");
        when(accountRepository.getUser(userDTO)).thenThrow(new UserNotFoundException(userDTO.getUserName()));
        //act & assert
        assertThrows(UserNotFoundException.class, () -> {
            accountService.loginUser(userDTO);
        });
        verify((accountRepository), atLeastOnce()).getUser(userDTO);
    }

    @Test
    public void testServiceShouldGetBalanceSuccessfully(){
        //arrange
        User user = new User("Antonio Gomez Sar", "antonio.gom", "1234");
        Account account = new Account(user, 100.00);
        AccountBalanceResponseDTO expectedResponse = new AccountBalanceResponseDTO(user.getUserName(), 100.00);
        when(accountRepository.getActiveUserAccount()).thenReturn(account);
        //act
        ResponseEntity<AccountBalanceResponseDTO> currentResponse = accountService.getBalance();
        //assert
        verify((accountRepository), atLeastOnce()).getActiveUserAccount();
        assertEquals(HttpStatus.OK, currentResponse.getStatusCode());
        assertEquals(expectedResponse.getUserName(), currentResponse.getBody().getUserName());
        assertEquals(expectedResponse.getBalance(), currentResponse.getBody().getBalance());
    }
}
