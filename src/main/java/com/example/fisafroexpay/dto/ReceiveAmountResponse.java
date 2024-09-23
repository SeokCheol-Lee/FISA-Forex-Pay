package com.example.fisafroexpay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReceiveAmountResponse {
    private String currencyCode;
    private BigDecimal amount;
}
