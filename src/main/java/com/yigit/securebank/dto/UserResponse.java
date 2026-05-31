package com.yigit.securebank.dto;

import com.yigit.securebank.entity.Role;

import java.math.BigDecimal;

public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private Role role;
    private BigDecimal balance;

    public UserResponse(Long id, String username, String email, Role role, BigDecimal balance) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
