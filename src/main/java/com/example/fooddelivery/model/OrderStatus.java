package com.example.fooddelivery.model;

import java.util.Set;

public enum OrderStatus {
    PLACED, ACCEPTED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED;

    public boolean canMoveTo(OrderStatus next) {
        return switch (this) {
            case PLACED -> Set.of(ACCEPTED, CANCELLED).contains(next);
            case ACCEPTED -> Set.of(PREPARING, CANCELLED).contains(next);
            case PREPARING -> next == OUT_FOR_DELIVERY;
            case OUT_FOR_DELIVERY -> next == DELIVERED;
            case DELIVERED, CANCELLED -> false;
        };
    }
}
