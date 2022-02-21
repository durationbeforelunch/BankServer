package com.bank.server.rest;

import com.bank.server.dto.AccountCreateDto;
import com.bank.server.dto.AccountMinInfoDto;
import com.bank.server.entity.Account;
import com.bank.server.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
*
* rest контроллер, лучше работать в postman, возвращает json
*
* */

@RestController
@RequiredArgsConstructor
public class BankController {

    private final IAccountService accountService;

    // Маппинг для получения полной информации о сущности
    @GetMapping("/account/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Account> getAccountInfo(@PathVariable Integer id) {
        return accountService.findById(id);
    }

    // Маппинг для обновления некоторой информации в сущности
    @PatchMapping("/account/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Account> patchAccountInfo(@PathVariable Integer id, @RequestBody AccountCreateDto accountCreateDto) {
        return accountService.patchAccountInfo(id, accountCreateDto);
    }
    // Маппинг для получения минимальной информации по всем сущностям
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('developers:read')")
    public List<AccountMinInfoDto> getAllAccounts() {
        return accountService.findAll();
    }

/*    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('developers:write')")
    public AccountMinInfoDto createAccount(@ModelAttribute("account") AccountCreateDto account) {
        return accountService.createAccount(account);
    }*/

    // Маппинг для удаления сущности
    @DeleteMapping("/account/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<String> deleteAccount(@PathVariable Integer id) {
        return accountService.deleteAccountById(id);
    }

}