package com.javalab.clothshop.service.category;

import com.javalab.clothshop.model.Category;
import com.javalab.clothshop.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AllCategoriesRetrievalServiceImpl implements AllCategoriesRetrievalService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> retrieveAll() {
        return null;
    }
}
