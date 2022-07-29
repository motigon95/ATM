package com.example.atm.service;

import com.example.atm.dto.AccountBalanceResponseDTO;
import com.example.atm.dto.AccountWithdrawResponseDTO;
import com.example.atm.dto.UserDTO;
import com.example.atm.model.Account;
import com.example.atm.model.User;
import com.example.atm.respository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Service
public class AccountService implements IAccountService {

    @Autowired
    IAccountRepository accountRepository;

    @Override
    public ResponseEntity<?> loginUser(UserDTO userDTO) {
        User user = accountRepository.getUser(userDTO);
        if (user != null) {
            accountRepository.setActiveUser(user);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<AccountBalanceResponseDTO> getBalance() {
        Account account = accountRepository.getActiveUserAccount();
        return new ResponseEntity<>(new AccountBalanceResponseDTO(account.getUser().getUserName(), account.getBalance()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountWithdrawResponseDTO> withdraw(double moneyToWithdraw) {
        Account account = accountRepository.getActiveUserAccount();
        if (account.getBalance() < moneyToWithdraw) return new ResponseEntity<>(new AccountWithdrawResponseDTO(0.00, account.getBalance()), HttpStatus.BAD_REQUEST);
        account = accountRepository.setNewBalance(account.getBalance() - moneyToWithdraw);
        return new ResponseEntity<>(new AccountWithdrawResponseDTO(moneyToWithdraw, account.getBalance()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountBalanceResponseDTO> deposit(double moneyToDeposit, String userName) {
        if(!accountRepository.isActiveUserAdmin())
            return ResponseEntity.badRequest().build();
        Account account = accountRepository.getUserAccount(userName);
        account = accountRepository.depositToUserAccount(userName, account.getBalance() + moneyToDeposit);
        return new ResponseEntity<>(new AccountBalanceResponseDTO(userName, account.getBalance()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> printUserInfo(String userName) throws FileNotFoundException, UnsupportedEncodingException {
        Account account = accountRepository.getUserAccount(userName);
        PrintWriter writer = new PrintWriter( userName + "Accinfo.txt", "UTF-8");
        writer.println("UserName: " + account.getUser().getUserName());
        writer.println("Current Balance: " + account.getBalance().toString());
        writer.close();
        return null;
    }
}
