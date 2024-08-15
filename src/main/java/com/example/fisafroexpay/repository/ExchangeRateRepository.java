package com.example.fisafroexpay.repository;

import com.example.fisafroexpay.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    @Query("SELECT e FROM ExchangeRate e WHERE e.targetCurrency = :targetCurrency ORDER BY e.createdAt DESC")
    ExchangeRate findByTargetCurrency(@Param("targetCurrency") String targetCurrency);

}
