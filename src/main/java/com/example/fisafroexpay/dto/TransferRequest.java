package com.example.fisafroexpay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    private String receiverAccountNumber;  // 받는 사람 계좌번호
    private String receiverName;
    private String receiverCurrencyCode;  // 받는 사람 통화 코드
    private Long amount;  // 송금 금액
}
