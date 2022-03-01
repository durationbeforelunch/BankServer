package com.bank.server.service;

import com.bank.server.dto.AccountCreateDto;
import com.bank.server.dto.AccountDto;
import com.bank.server.entity.Account;
import com.bank.server.exception.AccountAlreadyExists;
import com.bank.server.exception.AccountNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IAccountService {

    Account findById(Integer id) throws AccountNotFoundException;

    Account findByUsername(String username) throws AccountNotFoundException;

    List<Account> findAll();

    Account createAccount(AccountCreateDto dto) throws AccountAlreadyExists;

    void deleteAccountById(Integer id) throws AccountNotFoundException;

    Account patchAccountInfo(Integer id, AccountCreateDto dto) throws AccountNotFoundException;

    Account patchAccountInfo(String username, AccountDto dto) throws AccountNotFoundException;

    void setLastLoginNow(String username) throws AccountNotFoundException;

    void authenticate(HttpServletRequest request, AccountCreateDto dto) throws AccountNotFoundException, ServletException;

}
