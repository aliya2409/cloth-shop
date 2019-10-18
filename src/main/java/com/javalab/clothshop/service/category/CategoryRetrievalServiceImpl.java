package com.javalab.clothshop.service.category;

import com.javalab.clothshop.model.Category;
import com.javalab.clothshop.repository.CategoryRepository;
import com.javalab.clothshop.repository.exception.CategoryNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryRetrievalServiceImpl implements CategoryRetrievalService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category retrieveById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Could not find category with id: " + id));
    }
}
