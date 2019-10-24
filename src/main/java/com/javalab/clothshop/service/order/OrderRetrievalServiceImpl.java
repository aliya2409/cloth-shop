package com.javalab.clothshop.service.order;

import com.javalab.clothshop.model.Order;
import com.javalab.clothshop.repository.OrderRepository;
import com.javalab.clothshop.repository.exception.OrderNotFoundException;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class OrderRetrievalServiceImpl implements OrderRetrievalService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Order retrieveById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Could not find order with id: " + id));
        Hibernate.initialize(order.getItems());
        return order;
    }

    @Override
    public List<Order> retrieveAll() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
