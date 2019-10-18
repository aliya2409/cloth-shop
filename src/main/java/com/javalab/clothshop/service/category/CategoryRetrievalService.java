package com.javalab.clothshop.service.category;

import com.javalab.clothshop.model.Category;

import java.util.List;

public interface CategoryRetrievalService {

    Category retrieveById(Long id);

    List<Category> retrieveAll();
}
