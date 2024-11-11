package com.example.back_end.infrastructure.utils;

import java.text.Normalizer;

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

    public static String generateSlug(String value) {

        String normalized = Normalizer.normalize(value, java.text.Normalizer.Form.NFD);
        String slug = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        slug = slug.toLowerCase().replaceAll("[^a-z0-9\\s-]", "").replaceAll("\\s+", "-");

        return slug.replaceAll("^-+|-+$", "");
    }
}
