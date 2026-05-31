package com.yigit.securebank.service;

import com.yigit.securebank.dto.AmountRequest;
import com.yigit.securebank.dto.TransferRequest;
import com.yigit.securebank.entity.AppUser;
import com.yigit.securebank.entity.BankTransaction;
import com.yigit.securebank.entity.TransactionType;
import com.yigit.securebank.repository.AppUserRepository;
import com.yigit.securebank.repository.BankTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankingService {

    private final AppUserRepository userRepository;
    private final BankTransactionRepository transactionRepository;

    public BankingService(AppUserRepository userRepository,
                          BankTransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public AppUser deposit(String username, AmountRequest request) {
        validateAmount(request.getAmount());

        AppUser user = findUser(username);
        user.setBalance(user.getBalance().add(request.getAmount()));

        transactionRepository.save(new BankTransaction(
                username,
                TransactionType.DEPOSIT,
                request.getAmount(),
                "Demo money deposited"
        ));

        return userRepository.save(user);
    }

    @Transactional
    public AppUser withdraw(String username, AmountRequest request) {
        validateAmount(request.getAmount());

        AppUser user = findUser(username);

        if (user.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient demo balance");
        }

        user.setBalance(user.getBalance().subtract(request.getAmount()));

        transactionRepository.save(new BankTransaction(
                username,
                TransactionType.WITHDRAW,
                request.getAmount(),
                "Demo money withdrawn"
        ));

        return userRepository.save(user);
    }

    @Transactional
    public void transfer(String senderUsername, TransferRequest request) {
        validateAmount(request.getAmount());

        AppUser sender = findUser(senderUsername);
        AppUser receiver = findUser(request.getReceiverUsername());

        if (sender.getUsername().equals(receiver.getUsername())) {
            throw new RuntimeException("You cannot transfer money to yourself");
        }

        if (sender.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient demo balance");
        }

        sender.setBalance(sender.getBalance().subtract(request.getAmount()));
        receiver.setBalance(receiver.getBalance().add(request.getAmount()));

        userRepository.save(sender);
        userRepository.save(receiver);

        transactionRepository.save(new BankTransaction(
                sender.getUsername(),
                TransactionType.TRANSFER_OUT,
                request.getAmount(),
                "Transfer to " + receiver.getUsername()
        ));

        transactionRepository.save(new BankTransaction(
                receiver.getUsername(),
                TransactionType.TRANSFER_IN,
                request.getAmount(),
                "Transfer from " + sender.getUsername()
        ));
    }

    public List<BankTransaction> history(String username) {
        return transactionRepository.findByUsernameOrderByCreatedAtDesc(username);
    }

    private AppUser findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }
    }
}
