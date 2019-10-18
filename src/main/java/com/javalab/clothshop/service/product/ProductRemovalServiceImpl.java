package com.javalab.clothshop.service.product;

import com.javalab.clothshop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductRemovalServiceImpl implements ProductRemovalService {

    private final ProductRepository productRepository;

    @Override
    public void removeById(Long id) {
        productRepository.deleteById(id);
    }
}
