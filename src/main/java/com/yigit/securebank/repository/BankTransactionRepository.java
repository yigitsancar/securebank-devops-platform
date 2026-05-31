package com.yigit.securebank.repository;

import com.yigit.securebank.entity.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {
    List<BankTransaction> findByUsernameOrderByCreatedAtDesc(String username);
}
