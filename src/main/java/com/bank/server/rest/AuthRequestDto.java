package com.bank.server.rest;

import lombok.Data;

@Data
public class AuthRequestDto {

    private String email;
    private String password;

}
