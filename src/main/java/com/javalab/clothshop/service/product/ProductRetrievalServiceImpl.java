package com.javalab.clothshop.service.product;

import com.javalab.clothshop.model.Product;
import com.javalab.clothshop.repository.ProductRepository;
import com.javalab.clothshop.repository.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class ProductRetrievalServiceImpl implements ProductRetrievalService {

    private final ProductRepository productRepository;

    @Override
    public Product retrieveById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Could not find product with id: " + id));
    }

    @Override
    public List<Product> retrieveAll() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
