package com.arius.qrmenu.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.arius.qrmenu.common.Constants;

import lombok.Data;

@Data
public class DishesForm {

    private Long id;

    @NotBlank (message = Constants.DishesValidation.DISHES_NAME_NOT_BLANK)
    @Size (max = Constants.DishesValidation.DISHES_NAME_SIZE,
        message = Constants.DishesValidation.DISHES_NAME_MAX_LENGTH)
    private String name;

    @NotBlank (message = Constants.DishesValidation.IMAGE_NOT_BLANK)
    private String image;

    @NotBlank (message = Constants.DishesValidation.PRICE_NOT_BLANK)
    @Size (min = Constants.DishesValidation.PRICE_MIN_SIZE, message = Constants.DishesValidation.PRICE_MIN_LENGTH)
    private int price;

    @Size (max = Constants.DishesValidation.DESCRIPTION_SIZE,
        message = Constants.DishesValidation.DESCRIPTION_MAX_LENGTH)
    private String description;

    @NotBlank (message = Constants.DishesValidation.STATUS_NOT_BLANK)
    private int status;

    private Long categoryId;
}
