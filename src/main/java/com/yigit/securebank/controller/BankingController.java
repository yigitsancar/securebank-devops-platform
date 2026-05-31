package com.yigit.securebank.controller;

import com.yigit.securebank.dto.AmountRequest;
import com.yigit.securebank.dto.TransferRequest;
import com.yigit.securebank.dto.UserMapper;
import com.yigit.securebank.dto.UserResponse;
import com.yigit.securebank.entity.AppUser;
import com.yigit.securebank.entity.BankTransaction;
import com.yigit.securebank.service.BankingService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/banking")
public class BankingController {

    private final BankingService bankingService;

    public BankingController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    @PostMapping("/deposit")
    public UserResponse deposit(Authentication authentication,
                                @RequestBody AmountRequest request) {
        AppUser user = bankingService.deposit(authentication.getName(), request);
        return UserMapper.toUserResponse(user);
    }

    @PostMapping("/withdraw")
    public UserResponse withdraw(Authentication authentication,
                                 @RequestBody AmountRequest request) {
        AppUser user = bankingService.withdraw(authentication.getName(), request);
        return UserMapper.toUserResponse(user);
    }

    @PostMapping("/transfer")
    public Map<String, String> transfer(Authentication authentication,
                                        @RequestBody TransferRequest request) {
        bankingService.transfer(authentication.getName(), request);
        return Map.of("message", "Transfer completed successfully");
    }

    @GetMapping("/transactions")
    public List<BankTransaction> history(Authentication authentication) {
        return bankingService.history(authentication.getName());
    }
}
