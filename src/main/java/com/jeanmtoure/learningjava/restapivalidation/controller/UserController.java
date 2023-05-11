package com.jeanmtoure.learningjava.restapivalidation.controller;

import com.jeanmtoure.learningjava.restapivalidation.dto.UserRequest;
import com.jeanmtoure.learningjava.restapivalidation.entity.User;
import com.jeanmtoure.learningjava.restapivalidation.exception.UserNotFoundException;
import com.jeanmtoure.learningjava.restapivalidation.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserRequest request) {
        return new ResponseEntity<>(userService.saveUser(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@RequestBody @Valid UserRequest request, @PathVariable int id) throws UserNotFoundException {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }
}
