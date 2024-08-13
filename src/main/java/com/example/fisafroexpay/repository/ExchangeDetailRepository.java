package com.example.fisafroexpay.repository;

import com.example.fisafroexpay.entity.ExchangeDetail;
import com.example.fisafroexpay.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeDetailRepository extends JpaRepository<ExchangeDetail, Long> {
}
