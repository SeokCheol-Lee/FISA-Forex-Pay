package com.example.fisafroexpay.service;

import com.example.fisafroexpay.entity.Account;
import com.example.fisafroexpay.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account getSenderAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("송금자 ID가 유효하지 않습니다."));
    }

    public Account getSenderAccount(Long senderAccountId) {
        return accountRepository.findById(senderAccountId)
                .orElseThrow(() -> new RuntimeException("송금자 ID가 유효하지 않습니다."));
    }

}
