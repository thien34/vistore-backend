package com.example.back_end.utils;

import com.example.back_end.entity.Language;
import com.example.back_end.entity.LocalizedProperty;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class LocalizedPropertyUtils {
    private LocalizedPropertyUtils() {

    }

    public static <T> List<LocalizedProperty> createLocalizedPropertiesFromObject(T object, String localeKeyGroup, Language language, Long entityId) {
        List<LocalizedProperty> localizedProperties = new ArrayList<>();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (String.class.equals(field.getType())) {
                    String localeValue = (String) field.get(object);
                    String localeKey = field.getName();
                    LocalizedProperty localizedProperty = LocalizedProperty.builder()
                            .entityId(entityId) // Use the dynamic entityId
                            .localeKeyGroup(localeKeyGroup)
                            .localeKey(localeKey)
                            .localeValue(localeValue)
                            .language(language)
                            .build();
                    localizedProperties.add(localizedProperty);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error accessing field value", e);
            }
        }
        return localizedProperties;
    }
}