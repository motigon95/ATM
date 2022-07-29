package com.example.atm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class AccountBalanceResponseDTO {

    String userName;
    Double balance;

}
