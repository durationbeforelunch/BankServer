package com.bank.server.service;

import com.bank.server.dto.AccountCreateDto;
import com.bank.server.dto.AccountDto;
import com.bank.server.entity.Account;
import com.bank.server.exception.AccountAlreadyExists;
import com.bank.server.exception.AccountNotFoundException;
import com.bank.server.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

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
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    private Account saveAccount(AccountCreateDto dto) throws AccountAlreadyExists {

        if (accountRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new AccountAlreadyExists(String.format("Account with username - %s already exists", dto.getUsername()), HttpStatus.BAD_REQUEST);
        }

        Account account = Account.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .build();

        accountRepository.save(account);

        return account;

    }

    @Transactional @Override
    public Account createAccount(AccountCreateDto dto) throws AccountAlreadyExists {
        return saveAccount(dto);
    }

    @Override
    public void authenticate(HttpServletRequest request, AccountCreateDto dto) throws AccountNotFoundException, ServletException {
        Account account = findByUsername(dto.getUsername());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(account.getUsername(),
                                                        account.getPassword());

        authenticationToken.setDetails(new WebAuthenticationDetails(request));

        log.info(authenticationToken.isAuthenticated());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        log.info(authenticationToken.isAuthenticated());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info(authenticationToken.isAuthenticated());

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

    @Transactional @Override
    public Account patchAccountInfo(String username, AccountDto dto) throws AccountNotFoundException {

        Account account = findByUsername(username);

        if (dto != null) {

            if (dto.getBalance() != null && !dto.getBalance().equals(account.getBalance())) {
                account.setBalance(dto.getBalance());
            }
            if (dto.getRole() != null && !dto.getRole().equals(account.getRole())) {
                account.setRole(dto.getRole());
            }
            if (dto.getStatus() != null && !dto.getStatus().equals(account.getStatus())) {
                account.setStatus(dto.getStatus());
            }

        }

        return account;
    }

    @Transactional @Override
    public void setLastLoginNow(String username) throws AccountNotFoundException {
        Account account = findByUsername(username);
        account.setLastLogin(Instant.now());
    }

}

