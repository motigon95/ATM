package com.example.atm.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException (String message){
        super(message);
    }

}
