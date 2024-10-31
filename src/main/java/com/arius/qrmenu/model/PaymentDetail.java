package com.arius.qrmenu.model;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table (name = "payment_details")
public class PaymentDetail {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "number")
    private int number;

    @Column (name = "modified_at")
    private Timestamp modifiedAt;

    @Column (name = "total_price")
    private int totalPrice;

    @Column (name = "description")
    private String description;

    @Column (name = "status")
    private int status;

    @ManyToOne
    @JoinColumn (name = "table_id", referencedColumnName = "id")
    private DinnerTable tableId;

    @JsonIgnore
    @OneToMany (mappedBy = "paymentDetail")
    private List<Order> orders;
}
