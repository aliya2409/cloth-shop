package com.javalab.clothshop.controller;

import com.javalab.clothshop.model.Order;
import com.javalab.clothshop.service.order.OrderRemovalService;
import com.javalab.clothshop.service.order.OrderRetrievalService;
import com.javalab.clothshop.service.order.OrderSavingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
@AllArgsConstructor
public class OrderController {

    private final OrderRetrievalService orderRetrievalService;
    private final OrderRemovalService orderRemovalService;
    private final OrderSavingService orderSavingService;

    @GetMapping
    public List<Order> getAll() {
        return orderRetrievalService.retrieveAll();
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return orderRetrievalService.retrieveById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        orderRemovalService.removeById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity(null, headers, HttpStatus.OK);
    }

    @PostMapping("/{id}/purchase")
    public ResponseEntity purchase(@PathVariable Long id) {
        orderSavingService.purchase(orderRetrievalService.retrieveById(id));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity(null, headers, HttpStatus.OK);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity cancel(@PathVariable Long id) {
        orderSavingService.cancel(orderRetrievalService.retrieveById(id));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity(null, headers, HttpStatus.OK);
    }
}
