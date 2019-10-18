package com.javalab.clothshop.service.user;

import com.javalab.clothshop.model.User;
import com.javalab.clothshop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class AllUsersRetrievalServiceImpl implements AllUsersRetrievalService {

    private final UserRepository userRepository;

    @Override
    public List<User> retrieveAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
