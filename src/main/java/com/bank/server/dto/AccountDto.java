package com.bank.server.dto;

import com.bank.server.entity.Role;
import com.bank.server.entity.Status;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class AccountDto implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private BigDecimal balance;
    private Role role;
    private Status status;
    private Instant lastLogin;
}
