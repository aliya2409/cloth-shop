package com.javalab.clothshop.category;

import com.javalab.clothshop.model.Category;
import com.javalab.clothshop.repository.CategoryRepository;
import com.javalab.clothshop.repository.exception.NameTakenException;
import com.javalab.clothshop.service.category.CategoryRetrievalServiceImpl;
import com.javalab.clothshop.service.category.CategorySavingServiceImpl;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestCategoryServices {

    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategorySavingServiceImpl categorySavingService;
    @InjectMocks
    CategoryRetrievalServiceImpl categoryRetrievalService;
    private Category category;

    @BeforeEach
    public void setup() {
        category = new Category();
        category.setName("Utensils");
        category.setId(1L);
    }

    @Test
    public void test_throwsNameTaken() {
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));
        Category categoryToSave = new Category();
        categoryToSave.setName(category.getName());
        assertThrows(NameTakenException.class, () -> categorySavingService.save(categoryToSave));
    }

    @Test
    public void test_save() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        Category categoryToSave = new Category();
        categoryToSave.setName(category.getName());
        Category saved = categorySavingService.save(categoryToSave);
        assertEquals(category.getId(), saved.getId());
    }

    @Test
    public void test_getById() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        Category retrieved = categoryRetrievalService.retrieveById(1L);
        assertEquals(category.getName(), retrieved.getName());
    }

    @Test
    public void test_getAll() {
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        when(categoryRepository.findAll()).thenReturn(categories);
        List<Category> retrieved = categoryRetrievalService.retrieveAll();
        assertEquals(categories.size(), retrieved.size());
        assertTrue(retrieved.contains(category));
    }
}
