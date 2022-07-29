package com.example.atm.controller;

import com.example.atm.dto.AccountBalanceResponseDTO;
import com.example.atm.dto.AccountWithdrawResponseDTO;
import com.example.atm.dto.UserDTO;
import com.example.atm.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

@RequestMapping(path = "/atm")
@RestController
public class ATMController {

    @Autowired
    IAccountService accountService;

    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO){
        return accountService.loginUser(userDTO);
    }

    @GetMapping("/balance")
    public ResponseEntity<AccountBalanceResponseDTO> getBalance(){
        return accountService.getBalance();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountWithdrawResponseDTO> withdraw(@RequestParam double moneyToWithdraw){
        return accountService.withdraw(moneyToWithdraw);

    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountBalanceResponseDTO> deposit(@RequestParam double moneyToDeposit, @RequestParam String userName){
        return accountService.deposit(moneyToDeposit, userName);
    }

    @GetMapping("/print")
    public ResponseEntity<?> printUserInfo (@RequestParam String userName) throws FileNotFoundException, UnsupportedEncodingException {
        return accountService.printUserInfo(userName);
    }



}
