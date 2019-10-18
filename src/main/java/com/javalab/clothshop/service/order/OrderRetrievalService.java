package com.javalab.clothshop.service.order;

import com.javalab.clothshop.model.Order;

import java.util.List;

public interface OrderRetrievalService {

    Order retrieveById(Long id);

    List<Order> retrieveAll();
}
