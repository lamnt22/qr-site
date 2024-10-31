package com.arius.qrmenu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "orders")
public class Order {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "dish_id")
    private int dishId;

    @Column (name = "quantity")
    private int quantity;

    @Column (name = "price")
    private int price;

    @Column (name = "note")
    private String note;

    @Column (name = "status")
    private int status;

    @Column (name = "created_at")
    private String createdAt;

    @Column (name = "updated_at")
    private String updatedAt;

    @Column (name = "table_id")
    private int tableId;

    @Column (name = "table_name")
    private String tableName;

    @Column (name = "dish_name")
    private String dishName;

    @Column (name = "staff_id")
    private Long staffId;

    @Column (name = "payment_status")
    private int paymentStatus;

    @Column (name = "payment_id")
    private Long paymentId;

    @ManyToOne
    @JoinColumn (name = "table_id", insertable = false, updatable = false)
    private DinnerTable table;

    @ManyToOne
    @JoinColumn (name = "staff_id", insertable = false, updatable = false)
    private Staff staff;

    @ManyToOne
    @JoinColumn (name = "payment_id", insertable = false, updatable = false)
    private PaymentDetail paymentDetail;

}
