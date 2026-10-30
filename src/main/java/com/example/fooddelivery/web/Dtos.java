package com.example.fooddelivery.web;

import com.example.fooddelivery.model.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public final class Dtos {
    private Dtos() {}

    public record CreateRestaurant(@NotBlank String name, @NotBlank String cuisine, @NotBlank String address) {}
    public record CreateMenuItem(@NotBlank String name, @NotNull @Positive BigDecimal price) {}

    public record OrderItemRequest(@NotNull Long menuItemId, @Min(1) @Max(50) int quantity) {}
    public record PlaceOrder(@NotBlank String customerName, @NotBlank String deliveryAddress,
                             @NotNull Long restaurantId,
                             @NotEmpty List<@Valid OrderItemRequest> items) {}
    public record UpdateStatus(@NotNull OrderStatus status) {}

    public record LineResponse(String name, BigDecimal unitPrice, int quantity) {}
    public record OrderResponse(Long id, String customerName, String deliveryAddress, String restaurant,
                                List<LineResponse> items, BigDecimal total, OrderStatus status, Instant createdAt) {
        public static OrderResponse from(CustomerOrder o) {
            return new OrderResponse(o.getId(), o.getCustomerName(), o.getDeliveryAddress(),
                    o.getRestaurant().getName(),
                    o.getLines().stream().map(l -> new LineResponse(l.getName(), l.getUnitPrice(), l.getQuantity())).toList(),
                    o.getTotal(), o.getStatus(), o.getCreatedAt());
        }
    }
}
