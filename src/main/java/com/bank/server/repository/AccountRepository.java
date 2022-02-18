package com.bank.server.repository;

import com.bank.server.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    @NonNull Optional<Account> findById(@NonNull Integer id);

    @NonNull Optional<Account> findByUsername(@NonNull String username);

    @NonNull List<Account> findAll();

}