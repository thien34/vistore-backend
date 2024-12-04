package com.example.back_end.core.validator;

public class ValidateUtils {

    public static void validatePageable(int pageNo, int pageSize) {

        if (pageNo < 1) {
            throw new IllegalArgumentException("Số trang không được nhỏ hơn không.");
        }

        if (pageSize < 1) {
            throw new IllegalArgumentException("Kích thước trang không được nhỏ hơn một.");
        }
    }

}
