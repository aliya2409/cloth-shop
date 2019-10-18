package com.javalab.clothshop.service.user;

import com.javalab.clothshop.model.User;

public interface UserSavingService {

    User save(User user);
    User save(Long id, User user);
}
