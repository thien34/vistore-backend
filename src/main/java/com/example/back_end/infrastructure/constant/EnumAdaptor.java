package com.example.back_end.infrastructure.constant;

import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public final class EnumAdaptor {

    @SuppressWarnings("unchecked")
    public static <E> E valueOf(int constantValue, Class<E> enumClass) {
        Field valueField = getValueField(enumClass);

        E[] constants = enumClass.getEnumConstants();

        for (E constant : constants) {
            int current = getValue(valueField, constant);
            if (constantValue == current) {
                return constant;
            }
        }

        throw new IllegalArgumentException(String.format("not found: %d of %s", constantValue, enumClass.getName()));
    }

    public static <E> Optional<E> optionalOf(Integer constantValueNullable, Class<E> enumClass) {
        return constantValueNullable == null ? Optional.empty() : Optional.of(valueOf(constantValueNullable, enumClass));
    }

    public static <E> Optional<E> optionalOf(Integer constantValueNullable, Class<E> enumClass, int emptyValue) {
        return constantValueNullable == emptyValue ? Optional.empty() : optionalOf(constantValueNullable, enumClass);
    }

    public static <E extends Enum<?>> List<EnumConstant> convertToValueNameList(Class<E> enumClass, Enum[] enumConstants) {
        return convertToValueNameList(enumClass, enumClass.getEnumConstants());
    }

    private static <E> Field getValueField(Class<E> enumClass) {
        try {
            return enumClass.getField("value");
        } catch (SecurityException | NoSuchFieldException var2) {
            throw new IllegalArgumentException(String.format("value field is not defined: %s", enumClass.getName()), var2);
        }
    }

    private static <E> Field getNameIdField(Class<E> enumClass) {
        try {
            return enumClass.getField("nameId");
        } catch (SecurityException | NoSuchFieldException var2) {
            return null;
        }
    }

    private static <E> int getValue(Field valueField, E constant) {
        try {
            return (int) (Integer) valueField.get(constant);
        } catch (IllegalAccessException | IllegalArgumentException var4) {
            throw new RuntimeException(var4);
        }
    }

}
