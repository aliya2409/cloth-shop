package com.javalab.clothshop.repository;

import com.javalab.clothshop.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
