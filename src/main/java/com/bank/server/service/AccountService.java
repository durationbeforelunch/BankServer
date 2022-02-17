package com.bank.server.service;

import com.bank.server.dto.AccountCreateDto;
import com.bank.server.dto.AccountMinInfoDto;
import com.bank.server.entity.Account;
import com.bank.server.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    @Override
    public ResponseEntity<Account> findById(Integer id) {

        Optional<Account> account = accountRepository.findById(id);

        //noinspection OptionalIsPresent
        if (account.isPresent()) {

            return new ResponseEntity<>(account.get(), HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Override
    public List<AccountMinInfoDto> findAll() {

        return accountRepository.findAll()
                .stream()
                .map(AccountMinInfoDto::new)
                .toList();

    }

    @Transactional @Override
    public AccountMinInfoDto createAccount(AccountCreateDto accountCreateDto) {

        Account account = new Account();

        account.setUsername(accountCreateDto.getUsername());
        account.setPassword(accountCreateDto.getPassword());
        account.setEmail(accountCreateDto.getEmail());
        account.setLastLogin(Instant.now());

        accountRepository.save(account);

        return new AccountMinInfoDto(account);

    }

    @Transactional @Override
    public ResponseEntity<String> deleteAccountById(Integer id) {

        Optional<Account> account = accountRepository.findById(id);

        if (account.isPresent()) {

            accountRepository.deleteById(id);

            return new ResponseEntity<>(String.format("Client with id - %s was successfully removed", id), HttpStatus.ACCEPTED);

        }

        return new ResponseEntity<>(String.format("Account with id - %s was not found and therefore cannot be deleted", id), HttpStatus.NOT_FOUND);

    }

    @Transactional @Override
    public ResponseEntity<Account> patchAccountInfo(Integer id, AccountCreateDto dto) {

        Optional<Account> account = accountRepository.findById(id);

        if (account.isPresent()) {

            Account account1 = account.get();

            if (dto.getUsername() != null) {
                account1.setUsername(dto.getUsername());
            }

            if (dto.getPassword() != null) {
                account1.setPassword(dto.getPassword());
            }

            if (dto.getEmail() != null) {
                account1.setEmail(dto.getEmail());
            }

            account1.setLastLogin(Instant.now());

            return new ResponseEntity<>(account1, HttpStatus.OK);

        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}

