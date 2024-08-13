package com.example.fisafroexpay.dto;


import com.example.fisafroexpay.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {
    private Long transferId;  // 송금 ID
    private String userName;
    private String userAccountNumber;
    private String receiverName;
    private String receiverAccountNumber;
    private BigDecimal transferredAmount;  // 송금된 금액
    private BigDecimal totalTransferFee; // 환전 수수료 + 송금 수수료


}
