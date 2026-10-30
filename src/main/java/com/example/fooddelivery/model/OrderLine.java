package com.example.fooddelivery.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/** Snapshot of name/price at order time so later menu edits don't change past orders. */
@Entity
public class OrderLine {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long menuItemId;
    private String name;
    private BigDecimal unitPrice;
    private int quantity;

    protected OrderLine() {}
    public OrderLine(Long menuItemId, String name, BigDecimal unitPrice, int quantity) {
        this.menuItemId = menuItemId; this.name = name; this.unitPrice = unitPrice; this.quantity = quantity;
    }

    public Long getMenuItemId() { return menuItemId; }
    public String getName() { return name; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
}
