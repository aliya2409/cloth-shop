package com.javalab.clothshop.service.user;

import com.javalab.clothshop.model.User;
import com.javalab.clothshop.repository.UserRepository;
import com.javalab.clothshop.repository.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserSavingServiceImpl implements UserSavingService {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User save(Long id, User user) {
        if(userRepository.existsById(id)) {
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not find user with id: " + id);
        }
    }
}
