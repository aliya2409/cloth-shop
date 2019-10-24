package com.javalab.clothshop.service.category;

import com.javalab.clothshop.model.Category;
import com.javalab.clothshop.repository.CategoryRepository;
import com.javalab.clothshop.repository.exception.CategoryNotFoundException;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class CategoryRetrievalServiceImpl implements CategoryRetrievalService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category retrieveById(Long id) {
        Category category =  categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Could not find category with id: " + id));
        Hibernate.initialize(category.getProducts());
        return category;
    }

    @Override
    public List<Category> retrieveAll() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
