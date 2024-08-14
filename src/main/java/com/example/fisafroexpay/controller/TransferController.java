package com.example.fisafroexpay.controller;

import com.example.fisafroexpay.dto.TransferRequest;
import com.example.fisafroexpay.dto.TransferResponse;
import com.example.fisafroexpay.entity.User;
import com.example.fisafroexpay.service.TransferService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

//    // 1단계: 계산 결과 반환
//    @PostMapping("/check")
//    public ResponseEntity<TransferResponse> checkTransfer(
//            @AuthenticationPrincipal User user, @RequestBody TransferRequest req, HttpSession session) {
//        TransferResponse response = transferService.processTransfer(req, session);
//
//        return ResponseEntity.ok(response);
//    }
//
//    // 2단계: 최종 확정
//    @PostMapping("/confirm")
//    public ResponseEntity<TransferResponse> confirmTransfer(@RequestBody TransferRequest req, HttpSession session) {
//        TransferResponse response = transferService.confirmTransfer(req, session);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/send")
    public String processTransfer(@ModelAttribute TransferRequest req, Model model) {
        log.info("TransferController.processTransfer");
        TransferResponse response = transferService.processAndConfirmTransfer(req);
        model.addAttribute("transferResponse", response);

        return "confirmation";
    }


}
