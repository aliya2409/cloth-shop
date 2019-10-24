package com.javalab.clothshop.controller;

import com.javalab.clothshop.model.Product;
import com.javalab.clothshop.service.product.ProductRemovalService;
import com.javalab.clothshop.service.product.ProductRetrievalService;
import com.javalab.clothshop.service.product.ProductSavingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
@AllArgsConstructor
public class ProductController {

    private final ProductSavingService productSavingService;
    private final ProductRemovalService productRemovalService;
    private final ProductRetrievalService productRetrievalService;

    @GetMapping
    public List<Product> getAll() {
        return productRetrievalService.retrieveAll();
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return productSavingService.save(product);
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productRetrievalService.retrieveById(id);
    }

    @PutMapping("/{id}")
    public Product update(@RequestBody Product product, @PathVariable Long id) {
        return productSavingService.save(id, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        productRemovalService.removeById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity(null, headers, HttpStatus.OK);
    }
}
