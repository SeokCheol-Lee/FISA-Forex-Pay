package com.example.fisafroexpay.repository;

import com.example.fisafroexpay.entity.BankAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
  Optional<BankAccount> findBankAccountByBankAccountName(String bankAccountName);
}
