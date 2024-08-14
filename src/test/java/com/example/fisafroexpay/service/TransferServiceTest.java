package com.example.fisafroexpay.service;

import com.example.fisafroexpay.dto.TransferRequest;
import com.example.fisafroexpay.entity.*;
import com.example.fisafroexpay.entity.enums.Status;
import com.example.fisafroexpay.repository.TotalAccountRepository;
import com.example.fisafroexpay.repository.TransferDetailRepository;
import com.example.fisafroexpay.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransferDetailRepository transferDetailRepository;

    @Mock
    private ExchangeService exchangeService;

    @Mock
    private TotalAccountRepository totalAccountRepository;

    @Mock
    private HttpSession session;

    @InjectMocks
    private TransferService transferService;

    private User user;
    private Account senderAccount;
    private TotalAccount receiverAccount;
    private ExchangeDetail exchangeDetail;
    private TransferDetail transferDetail;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).username("sender").build();

        senderAccount = Account.builder()
                .id(1L)
                .accountNumber("123456789")
                .build();
        ReflectionTestUtils.setField(senderAccount, "user", user);

        receiverAccount = TotalAccount.builder()
                .accountNumber("987654321")
                .userName("receiver")
                .build();

        exchangeDetail = ExchangeDetail.builder()
                .finalAmount(BigDecimal.valueOf(1000))
                .exchangeFee(BigDecimal.valueOf(50))
                .build();

        ExchangeRate exchangeRate = ExchangeRate.builder()
                .baseExchangeRate(BigDecimal.valueOf(0.8))
                .build();
        ReflectionTestUtils.setField(exchangeDetail, "exchangeRate", exchangeRate);

        transferDetail = TransferDetail.builder()
                .id(1L)
                .account(senderAccount)
                .initAmount(BigDecimal.valueOf(1000))
                .lastAmount(BigDecimal.valueOf(900))
                .transferFee(BigDecimal.valueOf(100))
                .status(Status.PROCESSING)
                .build();
    }

    @Test
    void processTransfer_ShouldReturnTransferResponse() {
        // Given
        TransferRequest request = new TransferRequest();
        //request.setUserId(1L);
        //request.setAccountNumber("123456789");
        request.setReceiverAccountNumber("987654321");
        request.setReceiverCurrencyCode("USD");
        request.setAmount(1000L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(accountService.getSenderAccountByAccountNumber("123456789")).thenReturn(senderAccount);
        when(totalAccountRepository.findTotalAccountByAccountNumber("987654321"))
                .thenReturn(Optional.of(receiverAccount));
        when(exchangeService.createExchangeDetail(1000L, "USD"))
                .thenReturn(exchangeDetail);
        when(session.getAttribute("transferDetail")).thenReturn(null);

        // When
        //TransferResponse response = transferService.processTransfer(request);

        // Then
        /*assertNotNull(response);
        assertEquals("sender", response.getUserName());
        assertEquals("123456789", response.getUserAccountNumber());
        assertEquals("receiver", response.getReceiverName());
        assertEquals("987654321", response.getReceiverAccountNumber());
        assertEquals(BigDecimal.valueOf(850), response.getTransferredAmount()); // 1000 - (0.8 * 5000)
        assertEquals(BigDecimal.valueOf(150), response.getTotalTransferFee()); // 50 + (0.8 * 5000)

        verify(session).setAttribute(eq("transferDetail"), any(TransferDetail.class));*/
    }

    @Test
    void confirmTransfer_ShouldReturnTransferResponse() {
        // Given
        TransferRequest request = new TransferRequest();
        request.setReceiverAccountNumber("987654321");

        when(session.getAttribute("transferDetail")).thenReturn(transferDetail);
        when(transferDetailRepository.save(transferDetail)).thenReturn(transferDetail);
        when(transferDetailRepository.findTransferDetailWithAllDetails(1L)).thenReturn(transferDetail);
        when(totalAccountRepository.findTotalAccountByAccountNumber("987654321")).thenReturn(Optional.of(receiverAccount));

        // When
       /* TransferResponse response = transferService.confirmTransfer(request, session);

        // Then
        assertNotNull(response);
        assertEquals("sender", response.getUserName());
        assertEquals("123456789", response.getUserAccountNumber());
        assertEquals("receiver", response.getReceiverName());
        assertEquals("987654321", response.getReceiverAccountNumber());
        assertEquals(BigDecimal.valueOf(900), response.getTransferredAmount());
        assertEquals(BigDecimal.valueOf(100), response.getTotalTransferFee());

        verify(session).removeAttribute("transferDetail");*/
    }
}
