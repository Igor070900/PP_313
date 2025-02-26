package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Convertor convertor;

    @Autowired
    public UserServiceImpl(UserDao userRepository, PasswordEncoder passwordEncoder,
                           Convertor convertor)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.convertor = convertor;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void saveUser(User user, String[] roles) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            userRepository.save(new User(
                    user.getUsername(), passwordEncoder.encode(user.getPassword()),
                    user.getName(), user.getLastname(),
                    user.getAge(), user.getEmail(),
                    convertor.stringToSet(roles)
            ));
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateUser(User user, String[] roles) {
        User userToSave = new User(
                user.getUsername(), passwordEncoder.encode(user.getPassword()),
                user.getName(), user.getLastname(),
                user.getAge(), user.getEmail(),
                convertor.stringToSet(roles)
        );

        userToSave.setId(user.getId());

        userRepository.save(userToSave);
    }
}