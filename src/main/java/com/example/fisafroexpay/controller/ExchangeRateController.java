package com.example.fisafroexpay.controller;

import com.example.fisafroexpay.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;


    @GetMapping("/exchange-rates")
    public Map<String, BigDecimal> getExchangeRate(){
        return exchangeRateService.getNewestCurrencyRateMap();

    }

}
