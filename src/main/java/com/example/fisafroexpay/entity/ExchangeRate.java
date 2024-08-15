package com.example.fisafroexpay.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.*;
import org.hibernate.envers.AuditOverride;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
@ToString
public class ExchangeRate extends BaseEntity {

    @Id
    @Column(name = "exchange_rate_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String baseCurrency;
    private String targetCurrency;
    @Column(nullable = false, scale = 2)
    private BigDecimal baseExchangeRate;

    // 엔화만 100엔 당 n원 방식으로 저장되기 때문에 변경해야 할 필요 존재.
    public void convertJpyRate() {
        if ("JPY(100)".equals(this.targetCurrency)) {
            this.baseExchangeRate = this.baseExchangeRate.divide(
                    BigDecimal.valueOf(100),
                    2,
                    RoundingMode.HALF_EVEN);
        }

    }
}
