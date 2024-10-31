package com.arius.qrmenu.service;

import java.util.Optional;

import com.arius.qrmenu.model.PaymentDetail;

import org.springframework.data.domain.Page;

public interface PaymentDetailService {

    Optional<PaymentDetail> findById(Long id);

    PaymentDetail save(PaymentDetail entity);

    PaymentDetail updatePayment(Long id, PaymentDetail entity);

    Page<PaymentDetail> findAll(String sortBy, int page, int size);

    Page<PaymentDetail> findPaymentByTableIdAndModifiedAt(String sortBy, int page, int size, Integer tableId,
        String startDate, String endDate);

    Long findLatestId();

}
