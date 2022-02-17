package com.bank.server.service;

import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface IPaymentService {

    ResponseEntity<String> transferMoney(Integer from, Integer to, BigDecimal sum);

}
