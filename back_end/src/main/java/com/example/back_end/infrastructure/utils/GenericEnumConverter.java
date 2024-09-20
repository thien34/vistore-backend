package com.example.back_end.infrastructure.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class GenericEnumConverter<E extends Enum<E> & IdentifiableEnum<T>, T> implements AttributeConverter<E, T> {
    private final Class<E> enumClass;

    public GenericEnumConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public T convertToDatabaseColumn(E attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getId();
    }

    @Override
    public E convertToEntityAttribute(T dbData) {
        if (dbData == null) {
            return null;
        }
        for (E enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.getId().equals(dbData)) {
                return enumConstant;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + dbData);
    }

}
