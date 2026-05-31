package com.yigit.securebank.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private BigDecimal amount;

    private String description;

    private LocalDateTime createdAt = LocalDateTime.now();

    public BankTransaction() {
    }

    public BankTransaction(String username, TransactionType type, BigDecimal amount, String description) {
        this.username = username;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
