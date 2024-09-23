package com.example.fisafroexpay.controller;

import com.example.fisafroexpay.dto.*;
import com.example.fisafroexpay.entity.ExchangeDetail;
import com.example.fisafroexpay.entity.TransferDetail;
import com.example.fisafroexpay.service.ExchangeService;
import com.example.fisafroexpay.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/transfer")
@CrossOrigin(origins = "*")
public class TransferController {

  private final ExchangeService exchangeService;
  private final TransferService transferService;

  // 1단계: 계산 결과 반환
    /*@PostMapping("/check")
    public ResponseEntity<TransferResponse> checkTransfer(@RequestBody TransferRequest req) {
        TransferResponse response = transferService.processTransfer(req);
        return ResponseEntity.ok(response);
    }*/

  // 2단계: 최종 확정
    /*@PostMapping("/confirm")
    public ResponseEntity<TransferResponse> confirmTransfer(@RequestBody TransferRequest req) {
        TransferResponse response = transferService.confirmTransfer(req);
        return ResponseEntity.ok(response);
    }*/

  @PostMapping("/trade")
  public String tradeTransfer(@AuthenticationPrincipal SecurityUser user,
      @ModelAttribute TransferRequest req) {
    TransferDetail transferDetail = transferService.processTransfer(user.getUser().getId(), req);
    transferService.confirmTransfer(req, transferDetail);
    return "redirect:/confirmation.html";
  }

  @GetMapping("/confirm")
  public ResponseEntity<TransferResponse> tradeConfirm(@AuthenticationPrincipal SecurityUser user) {
    TransferResponse transferDetail = transferService.getTransferDetail(user.getUser());
    return ResponseEntity.ok(transferDetail);
  }

  @PostMapping("/calculate")
  @ResponseBody
  public ResponseEntity<ReceiveAmountResponse> getFinalAmount(@RequestBody ReceiveAmountRequest request){
    ExchangeDetail exchangeDetail = exchangeService.createExchangeDetail(request.getAmount(), request.getCurrencyCode());
    ReceiveAmountResponse response = transferService.getReceiveAmount(request.getCurrencyCode(), exchangeDetail);
    return ResponseEntity.ok(response);
  }
}
