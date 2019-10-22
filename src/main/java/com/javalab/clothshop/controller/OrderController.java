package com.javalab.clothshop.controller;

import com.javalab.clothshop.model.Order;
import com.javalab.clothshop.model.OrderItem;
import com.javalab.clothshop.service.order.OrderRemovalService;
import com.javalab.clothshop.service.order.OrderRetrievalService;
import com.javalab.clothshop.service.order.OrderSavingService;
import com.javalab.clothshop.service.order.item.OrderItemService;
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

    private final OrderSavingService orderSavingService;
    private final OrderRetrievalService orderRetrievalService;
    private final OrderRemovalService orderRemovalService;
    private final OrderItemService orderItemService;

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

    @GetMapping("/{id}/items")
    public List<OrderItem> getItems(@PathVariable Long id) {
        return orderRetrievalService.retrieveById(id).getItems();
    }

    @PostMapping("/{id}/items")
    public ResponseEntity addItem(@PathVariable Long id, @RequestBody OrderItem item) {
        Order order = orderRetrievalService.retrieveById(id);
        order.toBuilder().item(item).build();
        orderSavingService.save(order);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity(null, headers, HttpStatus.OK);
    }

    @GetMapping("/{oid}/items/{iid}")
    public OrderItem getItemById(@PathVariable("oid") Long orderId, @PathVariable("iid") Long itemId) {
        return orderItemService.retrieveById(itemId);
    }
}
