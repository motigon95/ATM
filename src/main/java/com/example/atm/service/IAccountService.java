package com.example.atm.service;

import com.example.atm.dto.AccountBalanceResponseDTO;
import com.example.atm.dto.AccountWithdrawResponseDTO;
import com.example.atm.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface IAccountService {
    ResponseEntity<?> loginUser(UserDTO userDTO);

    ResponseEntity<AccountBalanceResponseDTO> getBalance();

    ResponseEntity<AccountWithdrawResponseDTO> withdraw(double moneyToWithdraw);

    ResponseEntity<AccountBalanceResponseDTO> deposit(double moneyToDeposit, String userName);

    ResponseEntity<?> printUserInfo(String userName) throws FileNotFoundException, UnsupportedEncodingException;
}
