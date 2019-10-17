package com.javalab.clothshop.repository;

import com.javalab.clothshop.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
