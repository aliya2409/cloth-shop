package com.javalab.clothshop.repository;

import com.javalab.clothshop.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
