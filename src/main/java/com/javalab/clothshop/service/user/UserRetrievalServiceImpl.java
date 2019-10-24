package com.javalab.clothshop.service.user;

import com.javalab.clothshop.model.User;
import com.javalab.clothshop.repository.UserRepository;
import com.javalab.clothshop.repository.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class UserRetrievalServiceImpl implements UserRetrievalService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User retrieveById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Could not find user with id: " + id));
        Hibernate.initialize(user.getOrders());
        return user;
    }

    @Override
    public List<User> retrieveAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
