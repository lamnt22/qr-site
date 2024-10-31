package com.arius.qrmenu.service;

import java.util.List;
import java.util.Optional;

import com.arius.qrmenu.model.User;

public interface UserService {

    User createUser(User user);

    User getUserById(Long userId);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUser(Long userId);

    Optional<User> getUserByUsername(String username);
}
