package com.arius.qrmenu.form;

import com.arius.qrmenu.model.Dishes;

import lombok.Data;

@Data
public class DishesResponse {

    private Long id;

    private String name;

    private int price;

    private String description;

    private String image;
    
    private Long categoryId;

    public DishesResponse(final Dishes dishes) {
        this.id = dishes.getId();
        this.name = dishes.getName();
        this.price = dishes.getPrice();
        this.description = dishes.getDescription();
        this.image = dishes.getImage();
        this.categoryId=dishes.getCategoryId();
    }
}
