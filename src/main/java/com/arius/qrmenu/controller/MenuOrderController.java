package com.arius.qrmenu.controller;

import java.util.List;

import com.arius.qrmenu.form.OrderForm;
import com.arius.qrmenu.model.ChatMessage;
import com.arius.qrmenu.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/menu-order")
@CrossOrigin
public class MenuOrderController {
    
    @Autowired
    private OrderService orderService;

    @PostMapping ("/{tableId}")
    public ResponseEntity<?> createOrderTable(@RequestBody List<OrderForm> orderForms, @PathVariable int tableId) {

        ResponseEntity<?> response;
        try {
            List<Long> orderIds = orderService.saveAllOrders(orderForms, tableId);
            response = new ResponseEntity<>(orderIds, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("Error occurred while creating orders", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PostMapping ("/send-message")
    public ResponseEntity<?> sendMessage(@RequestBody ChatMessage message) {
       
        return orderService.sendMessage(message);
    }

}
