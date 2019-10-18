package com.javalab.clothshop.service.user;

import com.javalab.clothshop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRemovalServiceImpl implements UserRemovalService {

    private final UserRepository userRepository;

    @Override
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }
}
