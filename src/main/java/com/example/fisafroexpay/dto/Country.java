package com.example.fisafroexpay.dto;

import com.example.fisafroexpay.entity.ExchangeRate;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {
  private String countryName;
  private String currencyName;
  private float calcPrice;
  private String name;

  public ExchangeRate toEntity(String targetCurrency, float baseExchangeRate){
    BigDecimal exchangeRate = new BigDecimal(baseExchangeRate);

    return ExchangeRate.builder()
        .baseCurrency("KRW")
        .targetCurrency(targetCurrency)
        .baseExchangeRate(exchangeRate)
        .build();
  }
}
