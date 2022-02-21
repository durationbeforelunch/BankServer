package com.bank.server.service;

import com.bank.server.dto.AccountCreateDto;
import com.bank.server.dto.AccountMinInfoDto;
import com.bank.server.entity.Account;
import com.bank.server.exception.AccountAlreadyExists;
import com.bank.server.exception.AccountNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAccountService {

    Account findById(Integer id) throws AccountNotFoundException;

    Account findByUsername(String username) throws AccountNotFoundException;

    List<AccountMinInfoDto> findAll();

    AccountMinInfoDto createAccount(AccountCreateDto accountCreateDto) throws AccountAlreadyExists;

    void deleteAccountById(Integer id) throws AccountNotFoundException;

    Account patchAccountInfo(Integer id, AccountCreateDto dto) throws AccountNotFoundException;

}
