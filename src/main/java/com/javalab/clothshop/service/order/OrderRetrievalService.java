package com.javalab.clothshop.service.order;

import com.javalab.clothshop.model.Order;

public interface OrderRetrievalService {

    Order retrieveById(Long id);
}
