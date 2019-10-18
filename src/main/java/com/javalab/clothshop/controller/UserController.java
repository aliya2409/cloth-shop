package com.javalab.clothshop.controller;

import com.javalab.clothshop.model.Order;
import com.javalab.clothshop.model.User;
import com.javalab.clothshop.service.order.OrderSavingService;
import com.javalab.clothshop.service.user.UserRemovalService;
import com.javalab.clothshop.service.user.UserRetrievalService;
import com.javalab.clothshop.service.user.UserSavingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {

    private final UserRetrievalService userRetrievalService;
    private final UserRemovalService userRemovalService;
    private final UserSavingService userSavingService;
    private final OrderSavingService orderSavingService;


    @GetMapping
    public List<User> getAll() {
        return userRetrievalService.retrieveAll();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userSavingService.save(user);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userRetrievalService.retrieveById(id);
    }

    @PutMapping("/{id}")
    public User update(@RequestBody User user, @PathVariable Long id) {
        return userSavingService.save(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        userRemovalService.removeById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}/orders")
    public List<Order> getOrders(@PathVariable Long id) {
        return userRetrievalService.retrieveById(id).getOrders();
    }

    @PostMapping("/{id}/orders")
    public Order createOrder(@PathVariable Long id, @RequestBody Order order) {
        order.setUser(userRetrievalService.retrieveById(id));
        return orderSavingService.save(order);
    }

}
