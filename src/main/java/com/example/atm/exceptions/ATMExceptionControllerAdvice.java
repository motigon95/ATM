package com.example.atm.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ATMExceptionControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ATMExceptionControllerAdvice.class);

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ATMError handleException(UserNotFoundException ex) {
        logger.info(ex.getClass().getName()+" - "+ex.getMessage());
        return new ATMError(
                ex.getClass().getSimpleName(),
                "User not found",
                HttpStatus.NOT_FOUND.value()
        );
    }
}
