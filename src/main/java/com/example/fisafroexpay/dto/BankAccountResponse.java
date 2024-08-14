package com.example.fisafroexpay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountResponse {

  private TransferResponse transferResponse;
  private String bankAccountName;
  private String currencyCode;
  private Long balance;

}
