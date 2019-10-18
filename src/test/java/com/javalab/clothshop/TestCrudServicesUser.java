package com.javalab.clothshop;

import com.javalab.clothshop.model.User;
import com.javalab.clothshop.repository.UserRepository;
import com.javalab.clothshop.repository.exception.UserNotFoundException;
import com.javalab.clothshop.service.user.UserRemovalServiceImpl;
import com.javalab.clothshop.service.user.UserRetrievalServiceImpl;
import com.javalab.clothshop.service.user.UserSavingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestCrudServicesUser {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserRetrievalServiceImpl userRetrievalService;
    @InjectMocks
    UserSavingServiceImpl userSavingService;
    @InjectMocks
    UserRemovalServiceImpl userRemovalService;

    private User user = User.builder()
            .username("bastion")
            .firstName("Vincent")
            .lastName("Guerrero")
            .email("vince.@mail.com")
            .password("password")
            .phone("+74951234567")
            .build();

    @Before
    public void setup() {
        user.setId(1L);
    }

    @Test
    public void test_save() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        User userToSave = new User();
        userToSave.setEmail(user.getEmail());
        User saved = userSavingService.save(userToSave);
        assertEquals(user.getEmail(), saved.getEmail());
    }

    @Test
    public void test_retrieveById() {
        Long id = user.getId();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        User retrieved = userRetrievalService.retrieveById(id);
        assertEquals(user, retrieved);
    }

    @Test
    public void test_removeById() {
        userRemovalService.removeById(2L);
        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void test_retrieveAll() {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        List<User> retrieved = userRetrievalService.retrieveAll();
        assertEquals(1, retrieved.size());
        assertTrue(retrieved.contains(user));
    }

    @Test
    public void test_throwsUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userRetrievalService.retrieveById(2L));
    }
}
