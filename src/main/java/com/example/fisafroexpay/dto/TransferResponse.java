package com.example.fisafroexpay.dto;


import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {
    private Long transferId;  // 송금 ID
    private String userName;
    private String userAccountNumber;
    private String receiverName;
    private String receiverAccountNumber;
    private String currencyCode;
    private BigDecimal transferredAmount;  // 송금된 금액
    private BigDecimal totalTransferFee; // 환전 수수료 + 송금 수수료


}
