    package com.example.back_end.infrastructure.constant;

    import com.example.back_end.infrastructure.utils.IdentifiableEnum;
    import lombok.AllArgsConstructor;
    import lombok.Getter;

    @AllArgsConstructor
    @Getter
    public enum DiscountLimitationType implements IdentifiableEnum<Integer> {

        NONE(0), //Áp dụng cho toàn bộ khách hàng và không giới hạn  (infinity voucher && infinity limit)
        N_TIMES_ONLY(1), //Áp dụng cho toàn bộ khách hàng nhưng giới hạn số voucher( 100 voucher hết thì kết thúc voucher)
        N_TIMES_PER_CUSTOMER(2), //Áp dụng cho khách hàng nhưng giới hạn số lần mua(1 khách chỉ mua tối đa 5 sản phẩm)
        N_TIMES_TOTAL_AND_PER_CUSTOMER(3); //Áp dụng được cả giới hạn số lần lẫn giới hạn voucher
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
            throw new IllegalArgumentException("Invalid discount limitation type id: " + id);
        }
    }
