package com.example.fisafroexpay.service;

import com.example.fisafroexpay.dto.TransferRequest;
import com.example.fisafroexpay.dto.TransferResponse;
import com.example.fisafroexpay.entity.Account;
import com.example.fisafroexpay.entity.ExchangeDetail;
import com.example.fisafroexpay.entity.TransferDetail;
import com.example.fisafroexpay.entity.User;
import com.example.fisafroexpay.entity.enums.Status;
import com.example.fisafroexpay.repository.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferService {


    private final UserRepository userRepository;
    private final TransferDetailRepository transferDetailRepository;
    private final ExchangeService exchangeService;  // 환전 서비스 주입
    private final AccountRepository accountRepository;

    private static final BigDecimal TRANSFER_FEE_KRW = BigDecimal.valueOf(5000);

    public TransferDetail processTransfer(Long userId, TransferRequest req) {
        log.info("TransferService.processTransfer");
        // user 정보 확인
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("유저 ID 올바르지 않음"));

        Account senderAccount = accountRepository.findByUser(user)
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

        // 송금자의 계좌 확인
        //Account senderAccount = accountService.getSenderAccountByAccountNumber(req.getAccountNumber());
        //senderAccount.withdraw(req.getAmount());
        // 수신자 계좌 확인 또는 생성
        /*TotalAccount receiverAccount = totalAccountRepository.findTotalAccountByAccountNumber(req.getReceiverAccountNumber())
                .orElseThrow(() -> new RuntimeException("수신자 계좌가 유효하지 않습니다."));*/

        // 환율 계산 (환전 서비스 호출)
        ExchangeDetail exchangeDetail = exchangeService.createExchangeDetail(req.getAmount(),
            req.getReceiverCurrencyCode());

        // 송금 수수료 계산(외화) = 환율 * 5000원
        BigDecimal transferFee =
            TRANSFER_FEE_KRW.divide(exchangeDetail.getExchangeRate().getBaseExchangeRate(), 2,
                RoundingMode.HALF_EVEN);

        BigDecimal exchangedAmount = exchangeDetail.getFinalAmount();
        BigDecimal lastAmount = exchangedAmount.subtract(transferFee);
        BigDecimal totalTransferFee = transferFee.add(exchangeDetail.getExchangeFee());

        // TransferDetail 생성
        TransferDetail transferDetail = TransferDetail.builder()
            .account(senderAccount)
            .initAmount(exchangedAmount)
            .lastAmount(lastAmount)
            .receiver(req.getReceiverName())
            .receiverAccountNumber(req.getReceiverAccountNumber())
            .currencyCode(req.getReceiverCurrencyCode())
            .transferFee(TRANSFER_FEE_KRW)
            .status(Status.PROCESSING) // 임시로 저장 , Redis, memory cache 등
            .build();

        // response 생성
        TransferResponse response = new TransferResponse(
            null,
            user.getUsername(),
            senderAccount.getAccountNumber(),
            req.getReceiverName(),
            req.getReceiverAccountNumber(),
            req.getReceiverCurrencyCode(),
            lastAmount,
            totalTransferFee
        );

        // 세션에 transferDetail 저장
        //session.setAttribute("transferDetail", transferDetail);
        return transferDetail;
    }


    public TransferResponse confirmTransfer(TransferRequest req, TransferDetail transferDetail) {
        log.info("[TransferService.confirmTransfer]");

        if (transferDetail == null) {
            throw new RuntimeException("트랜잭션 정보를 찾을 수 없습니다.");
        }

        transferDetail.setStatus(Status.COMPLETED);
        transferDetail = transferDetailRepository.save(transferDetail);

        TransferDetail transferDetailWithFetchJoin = transferDetailRepository.findTransferDetailWithAllDetails(
            transferDetail.getId());

        // TransferResponse 어떻게 구성??
        /*TotalAccount totalAccount = totalAccountRepository.findTotalAccountByAccountNumber(req.getReceiverAccountNumber())
                .orElseThrow(() -> new RuntimeException("수신자 계좌가 유효하지 않습니다."));*/

        return new TransferResponse(
            transferDetailWithFetchJoin.getId(),
            transferDetailWithFetchJoin.getAccount().getUser().getUsername(),
            transferDetailWithFetchJoin.getAccount().getAccountNumber(),
            req.getReceiverName(),
            req.getReceiverAccountNumber(),
            req.getReceiverCurrencyCode(),
            transferDetailWithFetchJoin.getLastAmount(),
            transferDetailWithFetchJoin.getTransferFee()
        );


    }

    public TransferResponse getTransferDetail(User user) {
        Account account = accountRepository.findByUser(user)
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));
        TransferDetail transferDetail = transferDetailRepository.findTopByAccountOrderByCreatedAtDesc(account)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Account"));
        return TransferResponse.builder()
            .transferId(transferDetail.getId())
            .userName(transferDetail.getAccount().getUser().getUsername())
            .userAccountNumber(transferDetail.getAccount().getAccountNumber())
            .receiverAccountNumber(transferDetail.getReceiverAccountNumber())
            .receiverName(transferDetail.getReceiver())
            .currencyCode(transferDetail.getCurrencyCode())
            .transferredAmount(transferDetail.getLastAmount())
            .totalTransferFee(transferDetail.getTransferFee()).build();
    }

}
