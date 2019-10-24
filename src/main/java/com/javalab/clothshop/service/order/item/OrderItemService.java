package com.javalab.clothshop.service.order.item;

import com.javalab.clothshop.model.OrderItem;

import java.util.List;

public interface OrderItemService {

    OrderItem save(OrderItem orderItem);

    OrderItem retrieveById(Long id);

    List<OrderItem> retrieveAllByOrderId(Long orderId);

    void removeById(Long id);
}
