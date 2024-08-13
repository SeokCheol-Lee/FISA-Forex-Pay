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

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeDetailRepository exchangeDetailRepository;

    public ExchangeDetail createExchangeDetail(ExchangeRequest req) {
        log.info("[ExchangeService.createExchangeDetail]");

        // 환율 받아오기
        ExchangeRate exchangeRate = exchangeRateRepository.findByTargetCurrency(req.getCurrencyCode());

        //고객이 얼마를 보낼건지 받기(원화)
        BigDecimal initAmount = req.getAmount();

        // 환전 수수료 (원화, 환전 수수료율 1.5% 적용, 테이블에 저장)
        BigDecimal exchangeFeeKRW = initAmount.multiply(BigDecimal.valueOf(0.015));

        // 환전 수수료 (외화)
        BigDecimal exchangeFee = exchangeFeeKRW.multiply(exchangeRate.getBaseExchangeRate());

        // 최종 환전 금액 (초기 금액 * 매매 기준율 - 환전수수료)
        BigDecimal exchangedAmount = initAmount.multiply(exchangeRate.getBaseExchangeRate()).subtract(exchangeFee);


        return ExchangeDetail.builder()
                .exchangeRate(exchangeRate)
                .initAmount(initAmount)
                .exchangeFee(exchangeFeeKRW)
                .finalAmount(exchangedAmount)
                .status(Status.SUCCESS)
                .build();
    }

    public ExchangeDetail saveExchangeDetail(ExchangeDetail exchangeDetail) {
        return exchangeDetailRepository.save(exchangeDetail);
    }

    // TODO : controller 단에서 작성(Response 리턴 로직)
//    public ExchangeResponse getExchange(ExchangeRequest req) {
//        ExchangeDetail detail = createExchangeDetail(req);
//        return new ExchangeResponse(d, req.getCurrencyCode());
//    }
}
