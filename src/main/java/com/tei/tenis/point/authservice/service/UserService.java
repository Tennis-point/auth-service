package com.tei.tenis.point.authservice.service;

import com.tei.tenis.point.authservice.entity.UserRepository;
import com.tei.tenis.point.authservice.controller.exceptions.UserAlreadyExistsException;
import com.tei.tenis.point.authservice.controller.exceptions.UserNotFoundException;
import com.tei.tenis.point.authservice.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        if (userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()).isEmpty()) {
            userRepository.save(user);
        } else {
            throw new UserAlreadyExistsException("User already exists!");
        }
    }

    public User getUserByNameAndPassword(String name, String password) throws UserNotFoundException {
        Optional<User> user = userRepository.findByUsernameAndPassword(name, password);
        if (user.isEmpty()) {
            throw new UserNotFoundException("Invalid username or password!");
        }
        return user.get();
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId).get();
    }
}
