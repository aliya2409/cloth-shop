package com.javalab.clothshop.controller;

import com.javalab.clothshop.model.Product;
import com.javalab.clothshop.service.product.ProductSavingService;
import com.javalab.clothshop.service.vendor.VendorRetrievalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vendors/{id}/products")
@AllArgsConstructor
public class VendorProductsController {

    private final VendorRetrievalService vendorRetrievalService;
    private final ProductSavingService productSavingService;

    @GetMapping
    public List<Product> getProducts(@PathVariable Long id) {
        return vendorRetrievalService.retrieveById(id).getProducts();
    }

    @PostMapping
    public Product createProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setVendor(vendorRetrievalService.retrieveById(id));
        return productSavingService.save(product);
    }
}
