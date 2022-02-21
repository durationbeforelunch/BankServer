package com.bank.server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    public Account(AccountBuilder accountBuilder) {
        this.username = accountBuilder.username;
        this.password = accountBuilder.password;
        this.email = accountBuilder.email;
    }

    public static AccountBuilder builder() {
        return new AccountBuilder();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Setter
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Setter
    @Column(name = "password", nullable = false)
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
    private final Instant createdOn = Instant.now();

    @Setter
    @Column(name = "last_login")
    private Instant lastLogin = Instant.now();

    public static class AccountBuilder {

        private String username;
        private String password;
        private String email;

        public AccountBuilder username(String username) {
            this.username = username;
            return this;
        }

        public AccountBuilder password(String password) {
            this.password = password;
            return this;
        }

        public AccountBuilder email(String email) {
            this.email = email;
            return this;
        }

        public Account build() {
            return new Account(this);
        }

    }

}