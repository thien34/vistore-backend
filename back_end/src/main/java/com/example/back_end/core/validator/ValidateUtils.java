package com.example.back_end.core.validator;

public class ValidateUtils {

    public static void validatePageable(int pageNo, int pageSize) {

        if (pageNo < 1) {
            throw new IllegalArgumentException("Page number cannot be less than zero.");
        }

        if (pageSize < 1) {
            throw new IllegalArgumentException("Page size cannot be less than one.");
        }
    }

}
