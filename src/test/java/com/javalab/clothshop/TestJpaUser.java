package com.javalab.clothshop;

import com.javalab.clothshop.model.User;
import com.javalab.clothshop.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(ClothShopApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestJpaUser {

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


    @Before
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
