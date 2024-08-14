package com.example.fisafroexpay.service;

import com.example.fisafroexpay.entity.ExchangeDetail;
import com.example.fisafroexpay.entity.RevenueReport;
import com.example.fisafroexpay.entity.TransferDetail;
import com.example.fisafroexpay.repository.ExchangeDetailRepository;
import com.example.fisafroexpay.repository.RevenueReportRepository;
import com.example.fisafroexpay.repository.TransferDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RevenueReportService {
    // 리포지토리 또는 데이터베이스 접근 객체
    private final TransferDetailRepository transferDetailRepository;
    private final ExchangeDetailRepository exchangeDetailRepository;
    private final RevenueReportRepository revenueReportRepository;

    // 총 수익을 계산하고 저장하는 메서드
    public void calculateAndSaveTotalBenefit() {
        // 송금 요금 데이터 가져오기
        List<TransferDetail> transferDetails = transferDetailRepository.findAll();
        BigDecimal totalTransferFees = transferDetails.stream()
                .map(TransferDetail::getTransferFee)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 환전 요금 데이터 가져오기
        List<ExchangeDetail> exchangeDetails = exchangeDetailRepository.findAll();
        BigDecimal totalExchangeFees = exchangeDetails.stream()
                .map(ExchangeDetail::getExchangeFee)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalRevenue = totalTransferFees.add(totalExchangeFees);

        // RevenueReport 객체 생성 및 저장
        List<RevenueReport> revenueReports = transferDetails.stream()
                .flatMap(transferDetail -> exchangeDetails.stream()
                        .map(exchangeDetail -> RevenueReport.builder()
                                . transferDetail(transferDetail)
                                .exchangeDetail(exchangeDetail)
                                .totalBenefit(totalRevenue.longValue())  // BigDecimal을 long으로 변환
                                .build()))
                .collect(Collectors.toList());

        // 총 수익 데이터 저장
        revenueReportRepository.saveAll(revenueReports);
    }

}
