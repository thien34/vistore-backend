package com.example.back_end.infrastructure.constant;

import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public final class EnumConstant {

    private final int value;
    private final String fieldName;

    @ConstructorProperties({"value", "fieldName"})
    public EnumConstant(int value, String fieldName) {
        this.value = value;
        this.fieldName = fieldName;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof EnumConstant)) {
            return false;
        } else {
            EnumConstant other = (EnumConstant) o;
            if (this.getValue() != other.getValue()) {
                return false;
            } else {
                Object this$fieldName = this.getFieldName();
                Object other$fieldName = other.getFieldName();
                if (this$fieldName == null) {
                    return other$fieldName == null;
                } else return this$fieldName.equals(other$fieldName);
            }
        }
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + this.getValue();
        Object $fieldName = this.getFieldName();
        result = result * 59 + ($fieldName == null ? 43 : $fieldName.hashCode());
        return result;
    }

    public String toString() {
        return "EnumConstant(value=" + this.getValue() + ", fieldName=" + this.getFieldName() + ", localizedName=" + ")";
    }
}

