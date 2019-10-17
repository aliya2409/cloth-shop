package com.javalab.clothshop.repository;

import com.javalab.clothshop.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
