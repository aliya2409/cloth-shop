package com.javalab.clothshop.service.product;

import com.javalab.clothshop.model.Product;

import java.util.List;

public interface ProductRetrievalService {

    Product retrieveById(Long id);

    List<Product> retrieveAll();
}
