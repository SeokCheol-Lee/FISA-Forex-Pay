package com.example.fisafroexpay.service;

import com.example.fisafroexpay.dto.BankAccountRequest;
import com.example.fisafroexpay.dto.BankAccountResponse;
import com.example.fisafroexpay.dto.TransferResponse;
import com.example.fisafroexpay.entity.BankAccount;
import com.example.fisafroexpay.entity.TransferDetail;
import com.example.fisafroexpay.repository.BankAccountRepository;
import com.example.fisafroexpay.repository.TransferDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankAccountService {

  private final BankAccountRepository bankAccountRepository;
  private final TransferDetailRepository transferDetailRepository;

  @Transactional
  public void addBankAccount(BankAccountRequest bankAccountRequest) {
    TransferDetail transferDetail = transferDetailRepository.findById(
            bankAccountRequest.getTransferId())
        .orElseThrow(() -> new IllegalArgumentException("Invalid transfer response"));
    BankAccount bankAccount = BankAccount.builder()
        .transferDetail(transferDetail)
        .bankAccountName(bankAccountRequest.getBankAccountName())
        .currencyCode(bankAccountRequest.getCurrencyCode())
        .balance(bankAccountRequest.getBalance()).build();
    bankAccountRepository.save(bankAccount);
  }

  @Transactional
  public BankAccountResponse getBankAccount(String bankAccountName) {
    BankAccount bankAccount = bankAccountRepository.findBankAccountByBankAccountName(
            bankAccountName)
        .orElseThrow(() -> new IllegalArgumentException("Invalid bank account number"));
    TransferDetail transferDetail = bankAccount.getTransferDetail();
    TransferResponse transferResponse = TransferResponse.builder()
        .transferId(transferDetail.getId())
        .userName(String.valueOf(transferDetail.getAccount().getUser()))
        .userAccountNumber(transferDetail.getAccount().getAccountNumber())
        .receiverName(transferDetail.getReceiver())
        .receiverAccountNumber(transferDetail.getReceiverAccountNumber())
        .transferredAmount(transferDetail.getLastAmount())
        .totalTransferFee(transferDetail.getTransferFee()).build();
    return BankAccountResponse.builder()
        .transferResponse(transferResponse)
        .bankAccountName(bankAccount.getBankAccountName())
        .currencyCode(bankAccount.getCurrencyCode())
        .balance(bankAccount.getBalance()).build();
  }

  @Transactional
  public void updateBankAccount(Long bankAccountId, BankAccountRequest bankAccountResponse) {
    bankAccountRepository.findById(bankAccountId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid bank account id"));
    BankAccount bankAccount = BankAccount.builder()
        .id(bankAccountId)
        .bankAccountName(bankAccountResponse.getBankAccountName())
        .currencyCode(bankAccountResponse.getCurrencyCode())
        .balance(bankAccountResponse.getBalance()).build();
    bankAccountRepository.save(bankAccount);
  }

  @Transactional
  public void deleteBankAccount(Long accountNumber) {
    BankAccount bankAccount = bankAccountRepository.findById(accountNumber)
        .orElseThrow(() -> new IllegalArgumentException("Invalid bank account id"));
    bankAccountRepository.delete(bankAccount);

  }
}
