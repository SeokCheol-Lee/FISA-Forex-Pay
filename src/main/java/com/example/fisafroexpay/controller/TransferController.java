package com.example.fisafroexpay.controller;

import com.example.fisafroexpay.dto.TransferRequest;
import com.example.fisafroexpay.dto.TransferResponse;
import com.example.fisafroexpay.service.TransferService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

    // 1단계: 계산 결과 반환
    @PostMapping("/check")
    public ResponseEntity<TransferResponse> checkTransfer(@RequestBody TransferRequest req, HttpSession session) {
        TransferResponse response = transferService.processTransfer(req, session);
        return ResponseEntity.ok(response);
    }

    // 2단계: 최종 확정
    @PostMapping("/confirm")
    public ResponseEntity<TransferResponse> confirmTransfer(@RequestBody TransferRequest req, HttpSession session) {
        TransferResponse response = transferService.confirmTransfer(req, session);
        return ResponseEntity.ok(response);
    }
}
