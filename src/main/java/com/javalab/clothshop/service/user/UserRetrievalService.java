package com.javalab.clothshop.service.user;

import com.javalab.clothshop.model.User;

import java.util.List;

public interface UserRetrievalService {

    User retrieveBy(Long id);

    User retrieveBy(String email);

    List<User> retrieveAll();
}
