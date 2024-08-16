package com.example.fisafroexpay.controller;

import com.example.fisafroexpay.dto.BankAccountRequest;
import com.example.fisafroexpay.dto.BankAccountResponse;
import com.example.fisafroexpay.service.BankAccountService;
import com.example.fisafroexpay.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/bank-account")
@RestController
@RequiredArgsConstructor
public class BankAccountController {

  private final BankAccountService bankAccountService;
  private final ScrapService scrapService;

  @PostMapping
  public ResponseEntity<String> addBankAccount(@RequestBody BankAccountRequest bankAccountRequest) {
    bankAccountService.addBankAccount(bankAccountRequest);
    return ResponseEntity.ok("Bank Account Added");
  }

  @GetMapping
  public ResponseEntity<BankAccountResponse> getBankAccounts(@RequestParam String bankAccountId) {
    BankAccountResponse accountResponse = bankAccountService.getBankAccount(bankAccountId);
    return ResponseEntity.ok(accountResponse);
  }

  @PutMapping
  public ResponseEntity<String> updateBankAccount(@RequestParam Long bankAccountId,
      @RequestBody BankAccountRequest bankAccountRequest) {
    bankAccountService.updateBankAccount(bankAccountId,bankAccountRequest);
    return ResponseEntity.ok("Bank Account Updated");
  }

  @DeleteMapping
  public ResponseEntity<String> deleteBankAccount(@RequestParam Long bankAccountId) {
    bankAccountService.deleteBankAccount(bankAccountId);
    return ResponseEntity.ok("Bank Account Deleted");
  }
}
