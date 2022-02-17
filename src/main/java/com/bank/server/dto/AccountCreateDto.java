package com.bank.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class AccountCreateDto implements Serializable {

    private final String username;
    private final String password;
    private final String email;

}