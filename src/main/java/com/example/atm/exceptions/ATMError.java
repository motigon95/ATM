package com.example.atm.exceptions;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ATMError {

    private String error;
    private String message;
    private Integer status;




}
