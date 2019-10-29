package com.javalab.clothshop.controller;

import com.javalab.clothshop.model.User;
import com.javalab.clothshop.service.user.UserRemovalService;
import com.javalab.clothshop.service.user.UserRetrievalService;
import com.javalab.clothshop.service.user.UserSavingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserRetrievalService userRetrievalService;
    private final UserRemovalService userRemovalService;
    private final UserSavingService userSavingService;

    @GetMapping
    public List<User> getAll() {
        return userRetrievalService.retrieveAll();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userSavingService.save(user);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userRetrievalService.retrieveBy(id);
    }

    @PutMapping("/{id}")
    public User update(@RequestBody User user, @PathVariable Long id) {
        return userSavingService.save(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        userRemovalService.removeById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity(null, headers, HttpStatus.OK);
    }
}
