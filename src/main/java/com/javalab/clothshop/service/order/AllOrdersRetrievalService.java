package com.javalab.clothshop.service.order;

import com.javalab.clothshop.model.Order;

import java.util.List;

public interface AllOrdersRetrievalService {

    List<Order> retrieveAll();
}
