package com.example.fisafroexpay.controller;

import com.example.fisafroexpay.service.ExchangeRateService;
import com.example.fisafroexpay.service.ScrapService;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;
    private final ScrapService scrapService;


    @GetMapping("/exchange-rates")
    public Map<String, BigDecimal> getExchangeRate() throws IOException{
        return exchangeRateService.getNewestCurrencyRateMap();

    }

    @GetMapping("/scrap")
    public void getScrap() throws IOException {
        scrapService.scrap();
    }

}
