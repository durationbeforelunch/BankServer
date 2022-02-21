package com.bank.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AccountNotFoundException extends Exception {

    private final HttpStatus httpStatus;

    public AccountNotFoundException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }
}
