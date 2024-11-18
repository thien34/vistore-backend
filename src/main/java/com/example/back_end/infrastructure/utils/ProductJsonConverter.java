package com.example.back_end.infrastructure.utils;

import com.example.back_end.entity.Product;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductJsonConverter {

    public String convertToJson(Object object, Set<Object> processedObjects) {
        if (object == null) {
            return "null";
        }

        if (processedObjects.contains(object)) {
            return "\"<circular-reference>\"";
        }

        processedObjects.add(object);

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        Field[] fields = object.getClass().getDeclaredFields();
        boolean first = true;

        try {
            for (Field field : fields) {
                field.setAccessible(true);

                if ("product".equals(field.getName())) {
                    continue;
                }

                if (!first) {
                    jsonBuilder.append(",");
                }
                first = false;

                String fieldName = field.getName();
                Object fieldValue = field.get(object);

                jsonBuilder.append("\"").append(fieldName).append("\":");

                if (fieldValue instanceof String) {
                    jsonBuilder.append("\"").append(fieldValue).append("\"");
                } else if (fieldValue instanceof List) {
                    jsonBuilder.append("[");
                    List<?> list = (List<?>) fieldValue;
                    for (int i = 0; i < list.size(); i++) {
                        if (i > 0) {
                            jsonBuilder.append(",");
                        }
                        jsonBuilder.append(convertToJson(list.get(i), processedObjects));
                    }
                    jsonBuilder.append("]");
                } else if (fieldValue instanceof Number || fieldValue instanceof Boolean) {
                    jsonBuilder.append(fieldValue);
                } else {
                    jsonBuilder.append(convertToJson(fieldValue, processedObjects));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    public String convertProductToJson(Product product) {
        Set<Object> processedObjects = new HashSet<>();
        return convertToJson(product, processedObjects);
    }

}
