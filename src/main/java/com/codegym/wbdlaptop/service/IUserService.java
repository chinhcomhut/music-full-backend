package com.codegym.wbdlaptop.service;

import com.codegym.wbdlaptop.model.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String username);
    User findById(Long id);
    User findByUserId(Long id);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User save(User user);
}
