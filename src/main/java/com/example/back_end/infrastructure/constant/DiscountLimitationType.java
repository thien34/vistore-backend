    package com.example.back_end.infrastructure.constant;

    import com.example.back_end.infrastructure.utils.IdentifiableEnum;
    import lombok.AllArgsConstructor;
    import lombok.Getter;

    @AllArgsConstructor
    @Getter
    public enum DiscountLimitationType implements IdentifiableEnum<Integer> {

        N_TIMES_TOTAL_AND_PER_CUSTOMER(3);
        private final int id;

        @Override
        public Integer getId() {
            return this.id;
        }

        public static DiscountLimitationType getById(int id) {
            for (DiscountLimitationType type : values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Id loại giới hạn giảm giá không hợp lệ: " + id);
        }
    }
