package com.example.rest.controller;

import com.example.rest.dto.UsersDTO;
import com.example.rest.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Builder
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
@PostMapping("/logup")
public ResponseEntity<String> logUp(@RequestBody UsersDTO newUser) {
    userService.logUp(newUser);
    return ResponseEntity.ok("User signed up successfully.");
}
    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody UsersDTO loginUser) {
        return userService.logIn(loginUser);
    }

    @GetMapping("/private")
    public String helloWorldPrivate() {
        return "Hello World! from private endpoint";
    }
}