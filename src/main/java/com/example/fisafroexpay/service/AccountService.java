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

    private static String generateAccountNumber() {
        // UUID를 생성
        UUID uuid = UUID.randomUUID();

        // UUID에서 하이픈을 제거하고, 대문자로 변환
        String uuidString = uuid.toString().replace("-", "").toUpperCase();

        // 계좌 번호를 구성하는 로직 (예: 앞의 12자만 사용)
        String accountNumber = uuidString.substring(0, 12);

        return accountNumber;
    }

}
