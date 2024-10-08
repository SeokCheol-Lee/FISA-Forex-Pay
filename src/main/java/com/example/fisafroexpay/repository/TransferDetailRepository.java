package com.example.fisafroexpay.repository;

import com.example.fisafroexpay.entity.Account;
import com.example.fisafroexpay.entity.TransferDetail;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransferDetailRepository extends JpaRepository<TransferDetail, Long> {
    @Query("SELECT t FROM TransferDetail t " +
            "JOIN FETCH t.account a " +
            "JOIN FETCH a.user u " +
            "WHERE t.id = :id")
    TransferDetail findTransferDetailWithAllDetails(@Param("id") Long id);
    Optional<TransferDetail> findTopByAccountOrderByCreatedAtDesc(Account account);
}
