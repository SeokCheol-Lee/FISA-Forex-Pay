package com.example.fisafroexpay.repository;

import com.example.fisafroexpay.entity.TotalAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TotalAccountRepository extends JpaRepository<TotalAccount, Long> {
    Optional<TotalAccount> findTotalAccountByAccountNumber(String accountNumber);
}