package com.javalab.clothshop.controller;

import com.javalab.clothshop.model.Order;
import com.javalab.clothshop.service.order.OrderSavingService;
import com.javalab.clothshop.service.user.UserRetrievalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users/{id}/orders")
@AllArgsConstructor
public class UserOrdersController {

    private final UserRetrievalService userRetrievalService;
    private final OrderSavingService orderSavingService;

    @GetMapping
    public List<Order> getOrders(@PathVariable Long id) {
        return userRetrievalService.retrieveBy(id).getOrders();
    }

    @PostMapping
    public Order createOrder(@PathVariable Long id, @RequestBody Order order) {
        order.setUser(userRetrievalService.retrieveBy(id));
        return orderSavingService.save(order);
    }
}
