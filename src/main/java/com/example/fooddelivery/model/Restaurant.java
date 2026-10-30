package com.example.fooddelivery.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Restaurant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cuisine;
    private String address;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> menu = new ArrayList<>();

    protected Restaurant() {}
    public Restaurant(String name, String cuisine, String address) {
        this.name = name; this.cuisine = cuisine; this.address = address;
    }

    public void addItem(MenuItem item) { item.setRestaurant(this); menu.add(item); }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCuisine() { return cuisine; }
    public String getAddress() { return address; }
    public List<MenuItem> getMenu() { return menu; }
}
