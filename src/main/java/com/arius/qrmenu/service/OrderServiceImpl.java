package com.arius.qrmenu.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.arius.qrmenu.form.OrderForm;
import com.arius.qrmenu.form.OrderResponse;
import com.arius.qrmenu.model.ChatMessage;
import com.arius.qrmenu.model.DinnerTable;
import com.arius.qrmenu.model.Order;
import com.arius.qrmenu.repository.DinnerTableRepository;
import com.arius.qrmenu.repository.OrderRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DinnerTableRepository dinnerTableRepository;

    @Autowired
    SimpMessagingTemplate template;

    @Override
    public List<Long> saveAllOrders(List<OrderForm> orderForms, int tableId) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        List<Long> orderIds = new ArrayList<>();

        try {
            // Get info table by tableId
            Optional<DinnerTable> dinnerTable = dinnerTableRepository.findById(tableId);
            if (!dinnerTable.isPresent()) {
                throw new IllegalArgumentException("Table with ID " + tableId + " does not exist");
            }
            String tableName = dinnerTable.get()
                .getName();

            // Create list order from orderForms
            List<Order> orders = orderForms.stream()
                .map(orderForm -> {
                    Order order = new Order();
                    order.setTableId(tableId);
                    order.setDishId(orderForm.getId());
                    order.setQuantity(orderForm.getQuantity());
                    order.setStatus(1);
                    order.setPrice(orderForm.getPrice());
                    order.setDishName(orderForm.getName());
                    order.setCreatedAt(dateFormat.format(currentDate));
                    order.setTableName(tableName);
                    order.setPaymentStatus(0);
                    return order;
                })
                .collect(Collectors.toList());

            // Save list order to database
            List<Order> saveOrders = orderRepository.saveAll(orders);
            orderIds = saveOrders.stream()
                .map(Order::getId)
                .collect(Collectors.toList());

            return orderIds;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while saving orders", e);
        }
    }

    @Override
    public Map<String, Object> search(int currentPage, int size, String sortValue, String keyword, int status) {

        try {
            int page = currentPage > 0 ? currentPage - 1 : currentPage;
            Pageable pageable = PageRequest.of(page, size);
            String[] direc = sortValue.split(",");
            if (!StringUtils.isBlank(sortValue)) {
                Sort sort = Sort.by(direc[1].equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, direc[0]);
                pageable = PageRequest.of(page, size, sort);
            } else {
                Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
                pageable = PageRequest.of(page, size, sort);
            }
            Page<Order> orderPage = orderRepository.search(keyword, status, pageable);

            List<OrderResponse> listOrderResponse = new ArrayList<>();
            for (Order value : orderPage) {
                OrderResponse orderResponse = new OrderResponse(value);
                listOrderResponse.add(orderResponse);
            }
            Map<String, Object> mapData = new HashMap<>();
            mapData.put("content", listOrderResponse);
            mapData.put("totalElements", orderPage.getTotalElements());
            mapData.put("size", orderPage.getSize());

            return mapData;
        } catch (Exception e) {
            System.out.println("error: " + e.toString());
            throw new Error("Get Page Data Fall" + e.toString());
        }

    }

    @Override
    public Optional<OrderResponse> findOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        OrderResponse orderResponse = new OrderResponse(order.get());
        return Optional.of(orderResponse);
    }

    @Override
    public Order updateOrder(Order order, Long id) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Order existingOrder = orderRepository.findById(id)
            .get();
        existingOrder.setQuantity(order.getQuantity());
        existingOrder.setStatus(order.getStatus());
        existingOrder.setNote(order.getNote());
        existingOrder.setPaymentStatus(order.getPaymentStatus());
        existingOrder.setStaffId(order.getStaffId());
        existingOrder.setPaymentId(order.getPaymentId());
        existingOrder.setUpdatedAt(dateFormat.format(date));

        Order updateOrder = orderRepository.save(existingOrder);
        return updateOrder;
    }

    @Override
    public void deleteOrder(Long id) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Order existingOrder = orderRepository.findById(id)
            .get();
        existingOrder.setStatus(0);
        existingOrder.setUpdatedAt(dateFormat.format(date));

        orderRepository.save(existingOrder);
    }

    @Override
    public List<Order> findByTableIdAndPaymentStatus(int tableId, int paymnentStatus) {
        return orderRepository.findByTableIdAndPaymentStatus(tableId, paymnentStatus);
    }

    @Override
    public ResponseEntity<?> sendMessage(ChatMessage message) {
        Optional<DinnerTable> table = dinnerTableRepository.findById(message.getTableId());
        if (!table.isPresent()) {

            return new ResponseEntity<>("Table with ID " + message.getTableId() + " does not exist",
                HttpStatus.NOT_FOUND);
        }

        String tableName = table.get()
            .getName();
        message.setContent(tableName + " just ordered!");
        message.setTableName(tableName);
        template.convertAndSendToUser("notification", "/private", message);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    public List<Order> findByPaymentStatusAndPaymentId(int paymentStatus, Long paymentId) {
        return orderRepository.findByPaymentStatusAndPaymentId(paymentStatus, paymentId);
    }

}
