package com.example.fisafroexpay.repository;

import com.example.fisafroexpay.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    ExchangeRate findByTargetCurrency(String targetCurrency);

}
