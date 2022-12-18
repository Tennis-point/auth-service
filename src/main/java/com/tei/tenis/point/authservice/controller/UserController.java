package com.tei.tenis.point.authservice.controller;

import com.tei.tenis.point.authservice.controller.exceptions.UserNotFoundException;
import com.tei.tenis.point.authservice.entity.User;
import com.tei.tenis.point.authservice.jwt.JwtGeneratorInterface;
import com.tei.tenis.point.authservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
//@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, maxAge = 3600)
public class UserController {

    private final UserService userService;
    private final JwtGeneratorInterface jwtGenerator;

    public UserController(UserService userService, JwtGeneratorInterface jwtGenerator) {
        this.userService = userService;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> postUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new UserNotFoundException("UserName or Password is Empty");
        }
        User userData = userService.getUserByNameAndPassword(user.getUsername(), user.getPassword());
        if (userData == null) {
            throw new UserNotFoundException("UserName or Password is Invalid");
        }
        return new ResponseEntity<>(jwtGenerator.generateToken(userData), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

}