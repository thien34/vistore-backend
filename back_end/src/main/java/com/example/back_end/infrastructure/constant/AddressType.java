package com.example.back_end.infrastructure.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AddressType {

    HOME("Home"),
    BILLING("Billing"),
    SHIPPING("Shipping"),
    PICKUP("Pickup");

    private String label;
}
