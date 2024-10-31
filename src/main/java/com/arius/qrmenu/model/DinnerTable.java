package com.arius.qrmenu.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "dinner_tables")
public class DinnerTable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", unique = true)
    private String name;
    
    @Column(name = "seat_number")
    private int seatNumber;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "delete_flag")
    private int deleteFlag;
    
    @Column(name = "created_at")
    private String createdAt;
    
    @Column(name = "updated_at")
    private String updatedAt;
    
    @JsonIgnore
    @OneToMany(mappedBy = "table")
    private List<Order> listOrders;
    
    @JsonIgnore
    @OneToMany(mappedBy = "tableId")
    private List<PaymentDetail> listPayment;
}
