package com.javalab.clothshop.service.order;

import com.javalab.clothshop.model.Order;
import com.javalab.clothshop.model.OrderItem;
import com.javalab.clothshop.model.Product;
import com.javalab.clothshop.repository.OrderRepository;
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
        checkInventory(order).getItems().forEach(item -> updateProduct(item.getProduct().getId(), item));
        order.getItems().stream().peek(i -> i.setOrder(order)).forEach(orderItemService::save);
        return orderRepository.save(order);
    }

    private Order checkInventory(Order order) {
        List<OrderItem> unavailable = order.getItems().stream()
                .filter(item -> productRetrievalService.retrieveById(item.getProduct().getId()).getQuantity() < item.getQuantity())
                .collect(Collectors.toList());
        if (!unavailable.isEmpty()) {
            throw new UnavailableProductQuantityException("There is not enough quantity on hand for these items.", unavailable);
        } else {
            return order;
        }
    }

    private void updateProduct(Long productId, OrderItem ordered) {
        Product retrieved = productRetrievalService.retrieveById(productId);
        retrieved.setQuantity(retrieved.getQuantity() - ordered.getQuantity());
        productSavingService.save(retrieved);
    }
}
