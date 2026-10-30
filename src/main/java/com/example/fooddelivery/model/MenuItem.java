package com.example.fooddelivery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class MenuItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private boolean available = true;

    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore
    private Restaurant restaurant;

    protected MenuItem() {}
    public MenuItem(String name, BigDecimal price) { this.name = name; this.price = price; }

    public Long getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public boolean isAvailable() { return available; }
    public Restaurant getRestaurant() { return restaurant; }
    public void setRestaurant(Restaurant r) { this.restaurant = r; }
}
