package com.arius.qrmenu.common;

public class Constants {

    public static class ResponseMessage {
        public static final String SUCCESS = "SUCCESS";
        public static final String ERROR = "ERROR";
        public static final String ERROR_EXPIRED_TOKEN = "ERROR_EXPIRED_TOKEN";
        public static final String ACCOUNT_IS_LOCKED_BY_ADMIN = "ACCOUNT_IS_LOCKED_BY_ADMIN";
        public static final String ACCOUNT_IS_LOCKED = "ACCOUNT_IS_LOCKED";
        public static final String BAD_CREDITIAL = "BAD_CREDITIAL";
        public static final String NOT_FOUND_EMAIL = "NOT_FOUND_EMAIL";
        public static final String NEW_PASSWORD_SAME_CURRENT_PASSWORD = "NEW_PASSWORD_SAME_CURRENT_PASSWORD";
        public static final String ERROR_UPDATE_PASSWORD = "ERROR_UPDATE_PASSWORD";
        public static final String CURRENT_PASSWORD_NOT_MATCH = "CURRENT_PASSWORD_NOT_MATCH";
    }

    public static class DishesValidation {
        // validate function
        public static final String DISHES_NAME_NOT_BLANK = "DISHES_NAME_IS_REQUIRED";
        public static final int DISHES_NAME_SIZE = 100;
        public static final String DISHES_NAME_MAX_LENGTH = "DISHES_NAME_LENGTH_CAN_NOT_BE_LONGER_THAN_100";
        public static final int DESCRIPTION_SIZE = 100;
        public static final String DESCRIPTION_MAX_LENGTH = "DESCRIPTION_CAN_NOT_BE_LONGER_THAN_100";
        public static final String SIZE_NOT_BLANK = "SIZE_IS_REQUIRED";
        public static final String IMAGE_NOT_BLANK = "IMAGE_IS_REQUIRED";
        public static final String PRICE_NOT_BLANK = "PRICE_IS_REQUIRED";
        public static final int PRICE_MIN_SIZE = 1;
        public static final String PRICE_MIN_LENGTH = "PRICE MIN_LENGTH_CAN_NOT_BE_SMALLER_THAN_1";
        public static final String STATUS_NOT_BLANK = "STATUS_IS_REQUIRED";
        public static final String CATEGORY_NOT_BLANK = "CATEGORY_IS_REQUIRED";

        // validate error
        public static final String DISHES_NAME_EXIST = "DISHES_NAME_IS_EXISTED";
    }

    public static class DinnerTableValidation {
        public static final String TABLE_NAME_EXIST = "Table name is existed";
    }

    public static class Order {
        public static final String UPDATE_ORDER_FAILED = "Update order failed";
    }

    public static class StaffValidation {
        public static final String STAFF_NAME_NOT_BLANK = "STAFF_NAME_IS_REQUIRED";
        public static final String PHONE_NOT_BLANK = "PHONE_IS_REQUIRED";
        public static final String WORK_DATE_NOT_BLANK = "WORK_DATE_IS_REQUIRED";
        public static final String PRICE_NOT_BLANK = "PRICE_IS_REQUIRED";
        public static final String POSITION_MIN_LENGTH = "POSITION_IS_REQUIRED";
        public static final String STATUS_NOT_BLANK = "STATUS_IS_REQUIRED";
    }

    public static class CategoryValidation {
        public static final String CATEGORY_NAME_EXIST = "CATEGORY_NAME_IS_EXISTED";
        public static final String CATEGORY_NAME_NOT_BLANK = "CATEGORY_NAME_NOT_BLANK";
        public static final int CATEGORY_NAME_SIZE = 100;
        public static final String CATEGORY_NAME_MAX_LENGTH = "CATEGORY_NAME_LENGTH_CAN_NOT_BE_LONGER_THAN_100";
        public static final String STATUS_NOT_BLANK = "STATUS_IS_REQUIRED";
    }
}
