package com.bank.server.controller;

import com.bank.server.dto.AccountCreateDto;
import com.bank.server.dto.AccountMinInfoDto;
import com.bank.server.entity.Account;
import com.bank.server.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BankController {

    private final IAccountService accountService;

    @GetMapping("/account/{id}")
    public ResponseEntity<Account> getAccountInfo(@PathVariable Integer id) {
        return accountService.findById(id);
    }

    @PatchMapping("/account/{id}")
    public ResponseEntity<Account> patchAccountInfo(@PathVariable Integer id, @RequestBody AccountCreateDto accountCreateDto) {
        return accountService.patchAccountInfo(id, accountCreateDto);
    }

    @GetMapping("/all")
    public List<AccountMinInfoDto> getAllAccounts() {
        return accountService.findAll();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountMinInfoDto createAccount(@RequestBody AccountCreateDto account) {
        return accountService.createAccount(account);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Integer id) {
        return accountService.deleteAccountById(id);
    }

}