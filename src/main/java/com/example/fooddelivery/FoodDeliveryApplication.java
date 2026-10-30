package com.example.fooddelivery;

import com.example.fooddelivery.model.*;
import com.example.fooddelivery.repo.RestaurantRepository;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FoodDeliveryApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodDeliveryApplication.class, args);
    }

    /** Seed sample data so the API is usable immediately. */
    @Bean
    CommandLineRunner seed(RestaurantRepository repo) {
        return args -> {
            Restaurant r1 = new Restaurant("Spice Route", "Indian", "12 MG Road");
            r1.addItem(new MenuItem("Paneer Butter Masala", new BigDecimal("220.00")));
            r1.addItem(new MenuItem("Garlic Naan", new BigDecimal("45.00")));
            Restaurant r2 = new Restaurant("Pizza Corner", "Italian", "4 Church Street");
            r2.addItem(new MenuItem("Margherita", new BigDecimal("299.00")));
            r2.addItem(new MenuItem("Pepperoni", new BigDecimal("399.00")));
            repo.save(r1);
            repo.save(r2);
        };
    }
}
