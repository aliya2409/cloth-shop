package com.javalab.clothshop.repository;

import com.javalab.clothshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
