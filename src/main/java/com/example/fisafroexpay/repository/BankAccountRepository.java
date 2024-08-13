package com.example.fisafroexpay.repository;

import com.example.fisafroexpay.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
