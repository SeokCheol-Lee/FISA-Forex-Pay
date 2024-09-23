package com.example.fisafroexpay.repository;

import com.example.fisafroexpay.entity.Account;
import com.example.fisafroexpay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);
    Optional<Account> findByUser(User user);

}
