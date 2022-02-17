package com.bank.server.dto;

import com.bank.server.entity.Account;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class AccountMinInfoDto implements Serializable {

    private final Integer id;
    private final String username;

    public AccountMinInfoDto(Account account) {

        this.id = account.getId();
        this.username = account.getUsername();

    }
}
