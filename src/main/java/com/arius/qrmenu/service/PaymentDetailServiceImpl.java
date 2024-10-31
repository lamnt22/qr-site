package com.arius.qrmenu.service;

import java.sql.Timestamp;
import java.util.Optional;

import com.arius.qrmenu.model.PaymentDetail;
import com.arius.qrmenu.repository.PaymentDetailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailServiceImpl implements PaymentDetailService {

    @Autowired
    private PaymentDetailRepository repository;

    @Override
    public PaymentDetail save(PaymentDetail entity) {
        return repository.save(entity);
    }

    @Override
    public Page<PaymentDetail> findAll(String sortBy, int page, int size) {
        String[] sortParams = sortBy.split(",");
        String sortProperty = sortParams[0];
        Sort.Direction sortDirection = sortParams.length > 1
            ? Sort.Direction.fromString(sortParams[1])
            : Sort.Direction.DESC;

        // Construct Sort object
        Sort sortObj = Sort.by(sortDirection, sortProperty);
        Pageable pageable = PageRequest.of(page - 1, size, sortObj);
        return repository.findAll(pageable);
    }

    @Override
    public Optional<PaymentDetail> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<PaymentDetail> findPaymentByTableIdAndModifiedAt(String sortBy, int page, int size, Integer tableId,
        String startDate, String endDate) {

        String[] sortParams = sortBy.split(",");
        String sortProperty = sortParams[0];
        Sort.Direction sortDirection = sortParams.length > 1
            ? Sort.Direction.fromString(sortParams[1])
            : Sort.Direction.DESC;

        // Construct Sort object
        Sort sortObj = Sort.by(sortDirection, sortProperty);
        Pageable pageable = PageRequest.of(page - 1, size, sortObj);
        Timestamp startD = Timestamp.valueOf(startDate + " 00:00:00");
        Timestamp endD = Timestamp.valueOf(endDate + " 23:59:59");

        Page<PaymentDetail> listPaymentResult = repository.findPaymentByTableIdAndModifiedAt(tableId, startD, endD,
            pageable);

        return listPaymentResult;
    }

    @Override
    public PaymentDetail updatePayment(Long id, PaymentDetail entity) {
        PaymentDetail exitstingPayment = repository.findById(id)
            .get();
        exitstingPayment.setTableId(entity.getTableId());
        exitstingPayment.setNumber(entity.getNumber());
        exitstingPayment.setModifiedAt(entity.getModifiedAt());
        exitstingPayment.setTotalPrice(entity.getTotalPrice());
        exitstingPayment.setDescription(entity.getDescription());
        exitstingPayment.setStatus(entity.getStatus());

        PaymentDetail updatePaymentDetail = repository.save(exitstingPayment);
        return updatePaymentDetail;
    }

    @Override
    public Long findLatestId() {
        return repository.findLatestId();
    }
}
