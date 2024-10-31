package com.arius.qrmenu.repository;

import java.sql.Timestamp;

import com.arius.qrmenu.model.PaymentDetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long> {

    @Query ("SELECT p FROM PaymentDetail p WHERE p.tableId.id = ?1 AND p.modifiedAt BETWEEN ?2 AND ?3")
    Page<PaymentDetail> findPaymentByTableIdAndModifiedAt(Integer tableId, Timestamp startDate, Timestamp endDate,
        Pageable pageable);

    @Query ("SELECT MAX(p.id) FROM PaymentDetail p")
    Long findLatestId();
}
