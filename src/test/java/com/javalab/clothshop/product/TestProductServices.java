package com.javalab.clothshop.product;

import com.javalab.clothshop.model.Category;
import com.javalab.clothshop.model.Product;
import com.javalab.clothshop.model.Vendor;
import com.javalab.clothshop.repository.ProductRepository;
import com.javalab.clothshop.repository.exception.ProductNotFoundException;
import com.javalab.clothshop.service.product.ProductRemovalServiceImpl;
import com.javalab.clothshop.service.product.ProductRetrievalServiceImpl;
import com.javalab.clothshop.service.product.ProductSavingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestProductServices {

    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductSavingServiceImpl productSavingService;
    @InjectMocks
    ProductRemovalServiceImpl productRemovalService;
    @InjectMocks
    ProductRetrievalServiceImpl productRetrievalService;

    private Product product;
    private Vendor vendor;
    private Category category;

    @BeforeEach
    public void setup() {
        category = new Category();
        category.setName("Jeans");
        category.setId(1L);

        vendor = new Vendor();
        vendor.setName("Calvin Klein");
        vendor.setId(2L);

        product = Product.builder()
                .name("Skinny jeans")
                .vendor(vendor)
                .category(category)
                .price(350)
                .quantity(3).build();
    }

    @Test
    public void test_throwsNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productRetrievalService.retrieveById(1L));
    }

    @Test
    public void test_save() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product productToSave = new Product();
        productToSave.setName(product.getName());
        Product saved = productSavingService.save(productToSave);
        assertEquals(product.getId(), saved.getId());
    }

    @Test
    public void test_getById() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        Product retrieved = productRetrievalService.retrieveById(1L);
        assertEquals(product.getName(), retrieved.getName());
    }

    @Test
    public void test_getAll() {
        List<Product> categories = new ArrayList<>();
        categories.add(product);
        when(productRepository.findAll()).thenReturn(categories);
        List<Product> retrieved = productRetrievalService.retrieveAll();
        assertEquals(categories.size(), retrieved.size());
        assertTrue(retrieved.contains(product));
    }

    @Test
    public void test_delete() {
        productRemovalService.removeById(2L);
        verify(productRepository, times(1)).deleteById(anyLong());
    }
}
