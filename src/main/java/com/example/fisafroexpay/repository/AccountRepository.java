package com.example.fisafroexpay.repository;

import com.example.fisafroexpay.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
