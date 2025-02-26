package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    User findByUsername(String username);
    User findByEmail(String email);

    Optional<User> getUserById(Long id);

    void saveUser(User user);

    void saveUser(User user, String[] roles);

    void deleteUser(Long id);

    void updateUser(User user, String[] roles);
}