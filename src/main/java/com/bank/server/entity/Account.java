package com.bank.server.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Setter
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Setter
    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Setter
    @Column(name = "email", nullable = false, length = 128)
    private String email;

    @Setter
    @Column(name = "balance", precision = 131089)
    private BigDecimal balance = new BigDecimal(0);

    @Setter
    @Column(name = "role", length = 20)
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;

    @Setter
    @Column(name = "status", length = 20)
    @Enumerated(value = EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Column(name = "created_on")
    private Instant createdOn = Instant.now();

    @Setter
    @Column(name = "last_login")
    private Instant lastLogin = Instant.now();

}