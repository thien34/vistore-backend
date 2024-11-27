package com.example.back_end.infrastructure.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ShoppingCartType {
    PENDING(0),
    SUCCESSFUL(1);

    public final int id;
}
