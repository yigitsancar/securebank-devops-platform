package com.yigit.securebank.controller;

import com.yigit.securebank.dto.UserMapper;
import com.yigit.securebank.dto.UserResponse;
import com.yigit.securebank.entity.BankTransaction;
import com.yigit.securebank.repository.AppUserRepository;
import com.yigit.securebank.repository.BankTransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AppUserRepository userRepository;
    private final BankTransactionRepository transactionRepository;

    public AdminController(AppUserRepository userRepository,
                           BankTransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserResponse)
                .toList();
    }

    @GetMapping("/transactions")
    public List<BankTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
