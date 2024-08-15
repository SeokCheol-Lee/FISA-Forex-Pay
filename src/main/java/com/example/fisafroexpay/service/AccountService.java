package com.example.fisafroexpay.service;

import com.example.fisafroexpay.entity.Account;
import com.example.fisafroexpay.entity.User;
import com.example.fisafroexpay.repository.AccountRepository;
import jakarta.transaction.Transactional;
import java.util.UUID;
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

    @Transactional
    public String createAccount(User user) {
        Account account = Account.builder()
            .user(user)
            .name("test")
            .accountNumber(generateAccountNumber())
            .currencyCode("KRW")
            .balance(50000L).build();
        accountRepository.save(account);
        return account.getAccountNumber();
    }

    private String generateAccountNumber() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        uuidString = uuidString.replace("-", "");

        String numericUUID = uuidString.replaceAll("[a-f]", "0");

        String accountNumber = String.format("%4s-%4s-%4s-%4s",
            numericUUID.substring(0, 4),
            numericUUID.substring(4, 8),
            numericUUID.substring(8, 12),
            numericUUID.substring(12, 16));

        return accountNumber;
    }

}
