package com.javalab.clothshop.service.product;

import com.javalab.clothshop.model.Product;
import com.javalab.clothshop.repository.ProductRepository;
import com.javalab.clothshop.repository.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductSavingServiceImpl implements ProductSavingService {

    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product save(Long id, Product product) {
        if (productRepository.existsById(id)) {
            return productRepository.save(product);
        } else {
            throw new ProductNotFoundException("Could not find product with id: " + id);
        }
    }
}
