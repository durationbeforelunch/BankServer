package com.bank.server.service;

import com.bank.server.dto.AccountCreateDto;
import com.bank.server.dto.AccountMinInfoDto;
import com.bank.server.entity.Account;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAccountService {

    ResponseEntity<Account> findById(Integer id);

    List<AccountMinInfoDto> findAll();

    AccountMinInfoDto createAccount(AccountCreateDto accountCreateDto);

    ResponseEntity<String> deleteAccountById(Integer id);

    ResponseEntity<Account> patchAccountInfo(Integer id, AccountCreateDto dto);

}
