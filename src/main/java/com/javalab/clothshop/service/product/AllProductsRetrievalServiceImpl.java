package com.javalab.clothshop.service.product;

import com.javalab.clothshop.model.Product;
import com.javalab.clothshop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class AllProductsRetrievalServiceImpl implements AllProductsRetrievalService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> retrieveAll() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
