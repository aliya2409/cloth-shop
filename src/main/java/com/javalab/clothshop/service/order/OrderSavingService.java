package com.javalab.clothshop.service.order;

import com.javalab.clothshop.model.Order;

public interface OrderSavingService {

    Order save(Order order);

    void purchase(Order order);

    void cancel(Order order);
}
