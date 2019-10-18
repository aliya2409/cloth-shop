package com.javalab.clothshop.service.order;

import com.javalab.clothshop.model.Order;
import com.javalab.clothshop.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderSavingServiceImpl implements OrderSavingService {

    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
