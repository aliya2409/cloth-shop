package com.javalab.clothshop.service.category;

import com.javalab.clothshop.model.Category;
import com.javalab.clothshop.repository.CategoryRepository;
import com.javalab.clothshop.repository.exception.NameTakenException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategorySavingServiceImpl implements CategorySavingService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(checkNameUniqueness(category));
    }

    private Category checkNameUniqueness(Category category) {
        if(!categoryRepository.findByName(category.getName()).isPresent()) {
            return category;
        } else {
            throw new NameTakenException("Provided name is already taken: " + category.getName());
        }
    }
}
