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
@Table (name = "dishes")
public class Dishes {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "name", unique = true)
    private String name;

    @Column (name = "image")
    private String image;

    @Column (name = "price")
    private int price;

    @Column (name = "description")
    private String description;

    @Column (name = "delete_flag")
    private int deleteFlag;

    @Column (name = "status")
    private int status;

    @Column (name = "category_id")
    private Long categoryId;

    @ManyToOne
    @JoinColumn (name = "category_id", insertable = false, updatable = false)
    private Category category;

    // @ManyToMany(mappedBy = "dishs")
    // private Set<Order> orders;
}
