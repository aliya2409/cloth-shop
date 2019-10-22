package com.javalab.clothshop.service.product;

import com.javalab.clothshop.model.Product;

public interface ProductSavingService {

    Product save(Product product);

    Product save(Long id, Product product);
}
