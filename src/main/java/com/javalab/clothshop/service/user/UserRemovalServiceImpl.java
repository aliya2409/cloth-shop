package com.javalab.clothshop.service.user;

import com.javalab.clothshop.repository.UserRepository;
import com.javalab.clothshop.repository.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRemovalServiceImpl implements UserRemovalService {

    private final UserRepository userRepository;

    @Override
    public void removeById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("Could not find user with id: " + id);
        }
    }
}
