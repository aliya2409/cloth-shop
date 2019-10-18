package com.javalab.clothshop.service.user;

import com.javalab.clothshop.model.User;

import java.util.List;

public interface UserRetrievalService {

    User retrieveById(Long id);
    List<User> retrieveAll();
}
