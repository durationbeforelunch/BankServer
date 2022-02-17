package com.bank.server.service;

import com.bank.server.entity.Account;
import com.bank.server.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaymentService implements IPaymentService {

    private final AccountRepository accountRepository;

    @Transactional @Override
    public ResponseEntity<String> transferMoney(Integer fromId, Integer toId, BigDecimal sum) {

        Optional<Account> from = accountRepository.findById(fromId);
        Optional<Account> to = accountRepository.findById(toId);

        Account account1;
        Account account2;

        if (from.isEmpty() || to.isEmpty()) {
            return new ResponseEntity<>("One or both of accounts can't be found", HttpStatus.NOT_FOUND);
        } else {
            account1 = from.get();
            account2 = to.get();
        }

        if (sum.compareTo(BigDecimal.ZERO) == 0) {
            return new ResponseEntity<>("You can't send zero funds", HttpStatus.BAD_REQUEST);
        }

        if (sum.compareTo(BigDecimal.ZERO) < 0) {
            return new ResponseEntity<>("You can't send negative number of funds", HttpStatus.BAD_REQUEST);
        }

        if (account1.getBalance().compareTo(sum) >= 0) {
            account1.setBalance(account1.getBalance().subtract(sum));
            account2.setBalance(account2.getBalance().add(sum));
            return new ResponseEntity<>("Payment Successfully done!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not Sufficient funds", HttpStatus.BAD_REQUEST);
        }

    }
}