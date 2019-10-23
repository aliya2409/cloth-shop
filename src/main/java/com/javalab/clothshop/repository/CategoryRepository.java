package com.javalab.clothshop.repository;

import com.javalab.clothshop.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// TODO: I would suggest going for JpaRepository as it has a depth in terms of common functionality
//  for Rest APIs as well as web applications
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByName(String name);
}
