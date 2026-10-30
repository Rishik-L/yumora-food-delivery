package com.example.fooddelivery.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class CustomerOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String deliveryAddress;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderLine> lines = new ArrayList<>();

    private BigDecimal total = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PLACED;
    private Instant createdAt = Instant.now();

    protected CustomerOrder() {}
    public CustomerOrder(String customerName, String deliveryAddress, Restaurant restaurant) {
        this.customerName = customerName; this.deliveryAddress = deliveryAddress; this.restaurant = restaurant;
    }

    public void addLine(OrderLine line) {
        lines.add(line);
        total = total.add(line.getUnitPrice().multiply(BigDecimal.valueOf(line.getQuantity())));
    }

    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public Restaurant getRestaurant() { return restaurant; }
    public List<OrderLine> getLines() { return lines; }
    public BigDecimal getTotal() { return total; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public Instant getCreatedAt() { return createdAt; }
}
