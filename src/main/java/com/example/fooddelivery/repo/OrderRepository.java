package com.example.fooddelivery.repo;

import com.example.fooddelivery.model.CustomerOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByCustomerNameIgnoreCaseOrderByCreatedAtDesc(String customerName);
}
