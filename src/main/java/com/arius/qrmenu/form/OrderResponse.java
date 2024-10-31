package com.arius.qrmenu.form;

import com.arius.qrmenu.model.Order;

import lombok.Data;

@Data
public class OrderResponse {

    private Long id;

    private String tableName;

    private String dishName;

    private int quantity;

    private int price;

    private String createdAt;

    private String updatedAt;

    private int status;

    private String note;

    private Long staffId;
    
    private String staffName;

    public OrderResponse(final Order order) {

        this.id = order.getId();
        this.tableName = order.getTableName();
        this.dishName = order.getDishName();
        this.quantity = order.getQuantity();
        this.price = order.getPrice();
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();
        this.status = order.getStatus();
        this.note = order.getNote();
        this.staffId = order.getStaffId();
        this.staffName = order.getStaff() == null ? null : order.getStaff().getName();
    }

}
