package com.example.fooddelivery.web;

import com.example.fooddelivery.service.OrderService;
import com.example.fooddelivery.web.Dtos.*;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;
    public OrderController(OrderService service) { this.service = service; }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse place(@Valid @RequestBody PlaceOrder req) { return service.place(req); }

    @GetMapping("/{id}")
    public OrderResponse get(@PathVariable Long id) { return service.get(id); }

    @GetMapping
    public List<OrderResponse> byCustomer(@RequestParam String customer) { return service.byCustomer(customer); }

    @PatchMapping("/{id}/status")
    public OrderResponse updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateStatus req) {
        return service.updateStatus(id, req.status());
    }
}
