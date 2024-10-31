package com.arius.qrmenu.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.arius.qrmenu.form.OrderForm;
import com.arius.qrmenu.form.OrderResponse;
import com.arius.qrmenu.model.ChatMessage;
import com.arius.qrmenu.model.Order;

import org.springframework.http.ResponseEntity;

public interface OrderService {

    List<Long> saveAllOrders(List<OrderForm> orders, int tableId);

    Map<String, Object> search(int page, int size, String sortValue, String tableName, int status);

    Optional<OrderResponse> findOrderById(Long id);

    Order updateOrder(Order order, Long id);

    void deleteOrder(Long id);

    List<Order> findByTableIdAndPaymentStatus(int tableId, int paymnentStatus);

    ResponseEntity<?> sendMessage(ChatMessage message);

    List<Order> findByPaymentStatusAndPaymentId(int paymentStatus, Long paymentId);

}
