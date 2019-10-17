package com.javalab.clothshop.service.user;

import com.javalab.clothshop.model.User;
import com.javalab.clothshop.repository.UserRepository;
import com.javalab.clothshop.repository.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRetrievalServiceImpl implements UserRetrievalService {

    private final UserRepository userRepository;

    @Override
    public User retrieveById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Could not find user with id: " + id));
    }
}
