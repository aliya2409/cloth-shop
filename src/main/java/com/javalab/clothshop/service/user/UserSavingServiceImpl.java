package com.javalab.clothshop.service.user;

import com.javalab.clothshop.model.User;
import com.javalab.clothshop.repository.UserRepository;
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
}
