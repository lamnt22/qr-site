package com.arius.qrmenu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.arius.qrmenu.common.Constants;
import com.arius.qrmenu.model.Order;
import com.arius.qrmenu.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/order")
@CrossOrigin

public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    SimpMessagingTemplate template;

    @GetMapping ("/search")
    public ResponseEntity<?> getOrders(@RequestParam ("page") Optional<Integer> page,
        @RequestParam ("size") Optional<Integer> size, @RequestParam ("sortValue") Optional<String> sortValue,
        @RequestParam ("name") Optional<String> name, @RequestParam ("status") Optional<Integer> status) {
        Map<String, Object> mapData = new HashMap<>();
        mapData = orderService.search(page.orElse(1), size.orElse(50), sortValue.orElse(""), name.orElse(""),
            status.orElse(1));

        return new ResponseEntity<>(mapData, HttpStatus.OK);
    }

    @GetMapping ("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        return new ResponseEntity<>(orderService.findOrderById(orderId), HttpStatus.OK);
    }

    @PutMapping ("/{orderId}")
    public ResponseEntity<?> updateOrder(@RequestBody Order order, @PathVariable Long orderId) {

        try {
            orderService.updateOrder(order, orderId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Constants.Order.UPDATE_ORDER_FAILED, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> updateStatusOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok()
            .build();
    }

    @GetMapping ("/getOrderByTable")
    public ResponseEntity<List<Order>> getOrderByTable(@RequestParam ("tableId") final int tableId,
        @RequestParam ("paymentStatus") final int paymentStatus) {
        return new ResponseEntity<>(orderService.findByTableIdAndPaymentStatus(tableId, paymentStatus), HttpStatus.OK);
    }

    @GetMapping ("/getOrderByPaymentId")
    public ResponseEntity<List<Order>> getOrderByPaymentId(@RequestParam ("paymentStatus") final int paymentStatus,
        @RequestParam ("paymentId") final Long paymentId) {
        return new ResponseEntity<List<Order>>(orderService.findByPaymentStatusAndPaymentId(paymentStatus, paymentId),
            HttpStatus.OK);
    }
}
