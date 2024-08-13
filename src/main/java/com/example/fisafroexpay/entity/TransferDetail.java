package com.example.fisafroexpay.entity;

import com.example.fisafroexpay.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;

import lombok.*;
import org.hibernate.envers.AuditOverride;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class TransferDetail extends BaseEntity {

    @Id
    @Column(name = "transfer_detail_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
    private BigDecimal initAmount;
    private BigDecimal lastAmount;
    private BigDecimal transferFee;

    @Setter
    @Enumerated(EnumType.STRING)
    private Status status;
}
