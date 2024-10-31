package com.arius.qrmenu.controller;

import com.arius.qrmenu.model.PaymentDetail;
import com.arius.qrmenu.service.PaymentDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/payment")
@CrossOrigin
public class PaymentDetailController {

    @Autowired
    private PaymentDetailService paymentDetailService;

    @GetMapping ("")
    public ResponseEntity<Page<PaymentDetail>> getListPaymentDetail(@RequestParam ("page") final int page,
        @RequestParam ("size") final int size, @RequestParam ("sortBy") final String sortBy) {

        return new ResponseEntity<Page<PaymentDetail>>(paymentDetailService.findAll(sortBy, page, size), HttpStatus.OK);
    }

    @GetMapping ("/search")
    public ResponseEntity<Page<PaymentDetail>> searchPaymentDetail(@RequestParam ("tableId") final int tableId,
        @RequestParam ("startDate") final String startDate, @RequestParam ("endDate") final String endDate,
        @RequestParam ("page") final int page, @RequestParam ("size") final int size,
        @RequestParam ("sortBy") final String sortBy) {

        return new ResponseEntity<Page<PaymentDetail>>(
            paymentDetailService.findPaymentByTableIdAndModifiedAt(sortBy, page, size, tableId, startDate, endDate),
            HttpStatus.OK);
    }

    @PostMapping ("/add")
    public ResponseEntity<?> insertPaymentDetail(@RequestBody PaymentDetail paymentDetail) {

        try {
            PaymentDetail payment = paymentDetailService.save(paymentDetail);
            return new ResponseEntity<PaymentDetail>(payment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping ("/{id}")
    public ResponseEntity<PaymentDetail> getPaymentDetailById(@PathVariable ("id") final Long id) {

        if (id != null) {
            return new ResponseEntity<PaymentDetail>(paymentDetailService.findById(id)
                .get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<?> updatePaymentDetail(@PathVariable ("id") final Long id,
        @RequestBody PaymentDetail paymentDetail) {

        try {
            if (id != null) {
                PaymentDetail payment = paymentDetailService.updatePayment(id, paymentDetail);
                return new ResponseEntity<>(payment, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping ("/getLatestId")
    public ResponseEntity<Long> getLatestId() {
        Long paymentId = paymentDetailService.findLatestId();

        if (paymentId != null) {
            return new ResponseEntity<Long>(paymentId, HttpStatus.OK);
        } else {
            return new ResponseEntity<Long>((long) 1, HttpStatus.OK);
        }
    }
}
