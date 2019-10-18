package com.javalab.clothshop.service.order;

import com.javalab.clothshop.model.Order;
import com.javalab.clothshop.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class AllOrdersRetrievalServiceImpl implements AllOrdersRetrievalService {

    private final OrderRepository orderRepository;

    @Override
    public List<Order> retrieveAll() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
