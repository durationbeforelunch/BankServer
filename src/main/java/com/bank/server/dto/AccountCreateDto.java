package com.bank.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/*
*
* Обычная Dto использующаяся для регистрации пользователя или редактирования имеющихся Entity
*
* */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AccountCreateDto implements Serializable {

    private String username;
    private String password;
    private String email;

}