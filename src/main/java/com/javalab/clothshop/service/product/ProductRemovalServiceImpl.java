package com.javalab.clothshop.service.product;

import com.javalab.clothshop.repository.ProductRepository;
import com.javalab.clothshop.repository.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductRemovalServiceImpl implements ProductRemovalService {

    private final ProductRepository productRepository;

    @Override
    public void removeById(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ProductNotFoundException("Could not find product with id: " + id);
        }
    }
}
