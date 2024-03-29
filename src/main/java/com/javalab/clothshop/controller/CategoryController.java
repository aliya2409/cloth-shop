package com.javalab.clothshop.controller;

import com.javalab.clothshop.model.Category;
import com.javalab.clothshop.model.Product;
import com.javalab.clothshop.service.category.CategoryRetrievalService;
import com.javalab.clothshop.service.category.CategorySavingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryRetrievalService categoryRetrievalService;
    private final CategorySavingService categorySavingService;

    @GetMapping
    public List<Category> getAll() {
        return categoryRetrievalService.retrieveAll();
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categorySavingService.save(category);
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        return categoryRetrievalService.retrieveById(id);
    }

    @GetMapping("/{id}/products")
    public List<Product> getProducts(@PathVariable Long id) {
        return categoryRetrievalService.retrieveById(id).getProducts();
    }
}
