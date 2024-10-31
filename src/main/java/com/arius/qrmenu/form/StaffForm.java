package com.arius.qrmenu.form;

import javax.validation.constraints.NotBlank;

import com.arius.qrmenu.common.Constants;

import lombok.Data;

@Data
public class StaffForm {

    private Long id;

    @NotBlank (message = Constants.StaffValidation.STAFF_NAME_NOT_BLANK)
    private String name;

    @NotBlank (message = Constants.StaffValidation.PHONE_NOT_BLANK)
    private String phoneNumber;

    @NotBlank (message = Constants.StaffValidation.STATUS_NOT_BLANK)
    private int status;
}
