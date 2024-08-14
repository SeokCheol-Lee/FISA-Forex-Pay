package com.example.fisafroexpay.controller;

import com.example.fisafroexpay.dto.SecurityUser;
import com.example.fisafroexpay.dto.TransferRequest;
import com.example.fisafroexpay.dto.TransferResponse;
import com.example.fisafroexpay.entity.TransferDetail;
import com.example.fisafroexpay.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController {


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
    TransferResponse transferResponse = transferService.confirmTransfer(req, transferDetail);
    return "redirect:/confirmation.html";
  }
}
