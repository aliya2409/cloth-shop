package com.javalab.clothshop.service.order.item;

import com.javalab.clothshop.model.Order;
import com.javalab.clothshop.model.OrderItem;
import com.javalab.clothshop.repository.OrderItemRepository;
import com.javalab.clothshop.repository.exception.OrderItemNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem retrieveById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new OrderItemNotFoundException("Could not find order item with id: " + id));
    }

    @Override
    public List<OrderItem> retrieveAll(Order order) {
        return orderItemRepository.findAllByOrder(order);
    }

    @Override
    public void removeById(Long id) {
        orderItemRepository.deleteById(id);
    }
}
