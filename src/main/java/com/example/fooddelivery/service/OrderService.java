package com.example.fooddelivery.service;

import com.example.fooddelivery.model.*;
import com.example.fooddelivery.repo.*;
import com.example.fooddelivery.web.Dtos.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderService {
    private final OrderRepository orders;
    private final RestaurantRepository restaurants;

    public OrderService(OrderRepository orders, RestaurantRepository restaurants) {
        this.orders = orders; this.restaurants = restaurants;
    }

    @Transactional
    public OrderResponse place(PlaceOrder req) {
        Restaurant restaurant = restaurants.findById(req.restaurantId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
        CustomerOrder order = new CustomerOrder(req.customerName(), req.deliveryAddress(), restaurant);

        for (OrderItemRequest line : req.items()) {
            MenuItem item = restaurant.getMenu().stream()
                    .filter(m -> m.getId().equals(line.menuItemId())).findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Item " + line.menuItemId() + " is not on this restaurant's menu"));
            if (!item.isAvailable())
                throw new ResponseStatusException(HttpStatus.CONFLICT, item.getName() + " is unavailable");
            order.addLine(new OrderLine(item.getId(), item.getName(), item.getPrice(), line.quantity()));
        }
        return OrderResponse.from(orders.save(order));
    }

    @Transactional(readOnly = true)
    public OrderResponse get(Long id) { return OrderResponse.from(find(id)); }

    @Transactional(readOnly = true)
    public List<OrderResponse> byCustomer(String name) {
        return orders.findByCustomerNameIgnoreCaseOrderByCreatedAtDesc(name).stream().map(OrderResponse::from).toList();
    }

    @Transactional
    public OrderResponse updateStatus(Long id, OrderStatus next) {
        CustomerOrder order = find(id);
        if (!order.getStatus().canMoveTo(next))
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cannot move order from " + order.getStatus() + " to " + next);
        order.setStatus(next);
        return OrderResponse.from(order);
    }

    private CustomerOrder find(Long id) {
        return orders.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }
}
