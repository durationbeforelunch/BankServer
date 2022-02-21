package com.bank.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AccountAlreadyExists extends Exception {

    private final HttpStatus httpStatus;

    public AccountAlreadyExists(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

}
