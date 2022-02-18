package com.bank.server.security;

import com.bank.server.entity.Account;
import com.bank.server.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountDetailsServiceImplementation implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Account doesn't exists"));
        return SecurityAccount.fromAccount(account);
    }
}
