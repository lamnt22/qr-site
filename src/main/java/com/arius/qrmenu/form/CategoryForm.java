package com.arius.qrmenu.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.arius.qrmenu.common.Constants;

import lombok.Data;

@Data
public class CategoryForm {

    private Long id;

    @NotBlank (message = Constants.CategoryValidation.CATEGORY_NAME_NOT_BLANK)
    @Size (max = Constants.CategoryValidation.CATEGORY_NAME_SIZE,
        message = Constants.CategoryValidation.CATEGORY_NAME_MAX_LENGTH)
    private String name;

    @NotBlank (message = Constants.CategoryValidation.STATUS_NOT_BLANK)
    private int status;
}
