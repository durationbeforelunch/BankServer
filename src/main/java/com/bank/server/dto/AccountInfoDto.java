package com.bank.server.dto;

import com.bank.server.entity.Account;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountInfoDto {

    private String username;
    private BigDecimal balance;

    public AccountInfoDto(Account account) {

        username = account.getUsername();
        balance = account.getBalance();

    }

}
