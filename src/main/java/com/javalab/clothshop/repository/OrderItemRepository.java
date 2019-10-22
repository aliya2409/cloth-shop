package com.javalab.clothshop.repository;

import com.javalab.clothshop.model.Order;
import com.javalab.clothshop.model.OrderItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {

    List<OrderItem> findAllByOrder(Order order);
}
