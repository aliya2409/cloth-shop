package com.javalab.clothshop.user;

import com.javalab.clothshop.ClothShopApplication;
import com.javalab.clothshop.model.User;
import com.javalab.clothshop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(ClothShopApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestUserCrudRepository {

    @Autowired
    private UserRepository userRepository;
    private User user = User.builder()
            .username("bastion")
            .firstName("Vincent")
            .lastName("Guerrero")
            .email("vince.@mail.com")
            .password("password")
            .phone("+74951234567")
            .build();


    @BeforeEach
    public void test_saveUser() {
        user = userRepository.save(user);
        assertNotNull(user.getId());
    }

    @Test
    public void test_getUserById() {
        Optional<User> retrieved = userRepository.findById(user.getId());
        assertEquals(user, retrieved.get());
    }

    @Test
    public void test_getAllUsers() {
        List<User> userList = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        assertTrue(userList.contains(user));
    }

    @Test
    public void test_updateUser() {
        User updatable = user;
        updatable.setUsername("legion");
        userRepository.save(updatable);
        Optional<User> retrieved = userRepository.findById(updatable.getId());
        assertEquals(updatable, retrieved.get());
    }

    @Test
    public void test_deleteUser() {
        Long userId = user.getId();
        userRepository.deleteById(userId);
        Optional<User> retrieved = userRepository.findById(userId);
        assertTrue(!retrieved.isPresent());
    }
}
