package com.bank.server.service;

import com.bank.server.dto.AccountCreateDto;
import com.bank.server.dto.AccountMinInfoDto;
import com.bank.server.entity.Account;
import com.bank.server.exception.AccountAlreadyExists;
import com.bank.server.exception.AccountNotFoundException;
import com.bank.server.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Account findById(Integer id) throws AccountNotFoundException {
        return accountRepository.findById(id)
                        .orElseThrow(() -> new AccountNotFoundException(String.format("Account with id - %s not found", id), HttpStatus.NOT_FOUND));
    }

    @Override
    public Account findByUsername(String username) throws AccountNotFoundException {
        return accountRepository.findByUsername(username)
                        .orElseThrow(() -> new AccountNotFoundException(String.format("Account with username - %s not found", username), HttpStatus.NOT_FOUND));
    }

    @Override
    public List<AccountMinInfoDto> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(AccountMinInfoDto::new)
                .toList();
    }

    @Transactional @Override
    public AccountMinInfoDto createAccount(AccountCreateDto accountCreateDto) throws AccountAlreadyExists {

        if (accountRepository.findByUsername(accountCreateDto.getUsername()).isPresent()) {
            throw new AccountAlreadyExists(String.format("Account with username - %s already exists", accountCreateDto.getUsername()), HttpStatus.BAD_REQUEST);
        }

        Account account = Account.builder()
                .username(accountCreateDto.getUsername())
                .password(passwordEncoder.encode(accountCreateDto.getPassword()))
                .email(accountCreateDto.getEmail())
                .build();

        accountRepository.save(account);

        return new AccountMinInfoDto(account);

    }

    @Transactional @Override
    public void deleteAccountById(Integer id) throws AccountNotFoundException {

        if (findById(id) != null) {
            accountRepository.deleteById(id);
        } else {
            throw new AccountNotFoundException(String.format("Account with id - %s not found", id), HttpStatus.NOT_FOUND);
        }

    }

    @Transactional @Override
    public Account patchAccountInfo(Integer id, AccountCreateDto dto) throws AccountNotFoundException {

        try {

            Account account = findById(id);

            if (dto.getUsername() != null) {
                account.setUsername(dto.getUsername());
            }

            if (dto.getPassword() != null) {
                account.setPassword(dto.getPassword());
            }

            if (dto.getEmail() != null) {
                account.setEmail(dto.getEmail());
            }

            return account;

        } catch (AccountNotFoundException e) {
            throw new AccountNotFoundException(String.format("Account with id - %s not found", id), HttpStatus.NOT_FOUND);
        }

    }
}

