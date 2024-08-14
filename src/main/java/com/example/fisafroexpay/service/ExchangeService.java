package com.example.fisafroexpay.service;

import com.example.fisafroexpay.dto.ExchangeRequest;
import com.example.fisafroexpay.entity.ExchangeDetail;
import com.example.fisafroexpay.entity.ExchangeRate;
import com.example.fisafroexpay.entity.enums.Status;
import com.example.fisafroexpay.repository.ExchangeDetailRepository;
import com.example.fisafroexpay.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeDetailRepository exchangeDetailRepository;

    public ExchangeDetail createExchangeDetail(Long initAmount, String currencyCode) {
        log.info("[ExchangeService.createExchangeDetail]");

        // 환율 받아오기
        ExchangeRate exchangeRate = exchangeRateRepository.findByTargetCurrency(currencyCode);

        //고객이 얼마를 보낼건지 받기(원화)


        // 환전 수수료 (원화, 환전 수수료율 1.5% 적용, 테이블에 저장)
        BigDecimal initAmountDecimal = BigDecimal.valueOf(initAmount);
        BigDecimal exchangeFeeKRW = initAmountDecimal.multiply(BigDecimal.valueOf(0.015));

        // 환전 수수료 (외화)
        BigDecimal exchangeFee = exchangeFeeKRW.divide(exchangeRate.getBaseExchangeRate(),2 , RoundingMode.HALF_EVEN);

        // 최종 환전 금액 (초기 금액 * 매매 기준율 - 환전수수료)
        BigDecimal exchangedAmount = initAmountDecimal.divide(exchangeRate.getBaseExchangeRate(),2 , RoundingMode.HALF_EVEN).subtract(exchangeFee);


        ExchangeDetail exchangeDetail = ExchangeDetail.builder()
                .exchangeRate(exchangeRate)
                .initAmount(initAmount)
                .exchangeFee(exchangeFeeKRW)
                .finalAmount(exchangedAmount)
                .status(Status.COMPLETED)
                .build();
        exchangeDetailRepository.save(exchangeDetail);
        return exchangeDetail;

    }


}
