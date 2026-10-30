package com.example.fooddelivery.web;

import com.example.fooddelivery.model.*;
import com.example.fooddelivery.repo.RestaurantRepository;
import com.example.fooddelivery.web.Dtos.*;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantRepository repo;
    public RestaurantController(RestaurantRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Restaurant> list(@RequestParam(required = false) String cuisine) {
        return cuisine == null ? repo.findAll() : repo.findByCuisineIgnoreCase(cuisine);
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable Long id) { return find(id); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Restaurant create(@Valid @RequestBody CreateRestaurant req) {
        return repo.save(new Restaurant(req.name(), req.cuisine(), req.address()));
    }

    @PostMapping("/{id}/menu") @ResponseStatus(HttpStatus.CREATED)
    public Restaurant addItem(@PathVariable Long id, @Valid @RequestBody CreateMenuItem req) {
        Restaurant r = find(id);
        r.addItem(new MenuItem(req.name(), req.price()));
        return repo.save(r);
    }

    private Restaurant find(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
    }
}
