package com.example.atm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AccountWithdrawResponseDTO {

    private double moneyWithdrawn;
    private double currentBalance;

}
