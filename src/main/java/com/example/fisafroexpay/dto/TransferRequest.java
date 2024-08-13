package com.example.fisafroexpay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    private Long userId;
    private String accountNumber;  // 보내는 사람 계좌번호
    private String receiverAccountNumber;  // 받는 사람 계좌번호
    private String receiverCurrencyCode;  // 받는 사람 통화 코드
    private BigDecimal amount;  // 송금 금액
}
