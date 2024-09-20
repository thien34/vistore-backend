package com.example.back_end.infrastructure.utils;

public class StringUtils {

    /**
     * This method trims leading and trailing spaces and replaces multiple spaces within the string with a single space.
     *
     * @param str the input string to be normalized
     * @return a string with no leading/trailing spaces and no multiple spaces between words
     */
    public static String sanitizeText(String str) {
        return str.trim().replaceAll("\\s+", " ");
    }
}
