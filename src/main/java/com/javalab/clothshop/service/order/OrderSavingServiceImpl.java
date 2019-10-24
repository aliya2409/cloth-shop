package com.javalab.clothshop.service.order;

import com.javalab.clothshop.model.Order;
import com.javalab.clothshop.model.OrderItem;
import com.javalab.clothshop.model.OrderStatus;
import com.javalab.clothshop.model.Product;
import com.javalab.clothshop.repository.OrderRepository;
import com.javalab.clothshop.repository.exception.OrderCancellationException;
import com.javalab.clothshop.repository.exception.UnavailableProductQuantityException;
import com.javalab.clothshop.service.order.item.OrderItemService;
import com.javalab.clothshop.service.product.ProductRetrievalService;
import com.javalab.clothshop.service.product.ProductSavingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderSavingServiceImpl implements OrderSavingService {

    private final OrderRepository orderRepository;
    private final ProductRetrievalService productRetrievalService;
    private final ProductSavingService productSavingService;
    private final OrderItemService orderItemService;

    @Override
    @Transactional
    public Order save(Order order) {
        checkInventory(order).getItems().stream().peek(i -> i.setOrder(order)).forEach(orderItemService::save);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void purchase(Order order) {
        order.setStatus(OrderStatus.APPROVED);
        save(order);
        order.getItems().forEach(this::decreaseInventory);
    }

    @Override
    public void cancel(Order order) {
        OrderStatus orderStatus = order.getStatus();
        if (orderStatus == OrderStatus.PLACED) {
            order.setStatus(OrderStatus.CANCELED);
        } else if (orderStatus == OrderStatus.APPROVED) {
            order.setStatus(OrderStatus.CANCELED);
            order.getItems().forEach(this::increaseInventory);
        } else {
            throw new OrderCancellationException("Unable to cancel already delivered or canceled order.");
        }
        save(order);
    }

    private Order checkInventory(Order order) {
        List<OrderItem> unavailable = order.getItems().stream()
                .filter(item -> item.getProduct().getQuantity() < item.getQuantity())
                .collect(Collectors.toList());
        if (!unavailable.isEmpty()) {
            throw new UnavailableProductQuantityException("There is not enough quantity on hand for these items.", unavailable);
        } else {
            return order;
        }
    }

    private void decreaseInventory(OrderItem ordered) {
        Product retrieved = productRetrievalService.retrieveById(ordered.getProduct().getId());
        retrieved.setQuantity(retrieved.getQuantity() - ordered.getQuantity());
        productSavingService.save(retrieved);
    }

    private void increaseInventory(OrderItem ordered) {
        Product retrieved = productRetrievalService.retrieveById(ordered.getProduct().getId());
        retrieved.setQuantity(retrieved.getQuantity() + ordered.getQuantity());
        productSavingService.save(retrieved);
    }
}
