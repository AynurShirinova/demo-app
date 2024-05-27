package com.example.rest.service;

import com.example.rest.domain.User;
import com.example.rest.dto.UsersDTO;
import com.example.rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<String> logIn(UsersDTO usersDTO) {
        boolean isLoggedIn = userRepository.existsByMailAndPassword(usersDTO.getMail(), usersDTO.getPassword());

        if (isLoggedIn) {
            return ResponseEntity.ok("Login successful.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed. Please try again.");
        }
    }

    public ResponseEntity<String> logUp(UsersDTO usersDTO) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setMail(usersDTO.getMail());
        user.setUsername(usersDTO.getUsername());
        user.setPassword(usersDTO.getPassword());

        try {
            userRepository.save(user);
            return ResponseEntity.ok("User successfully registered.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while registering user.");
        }
    }
public Optional<User> getByUsername(String username) {
    return userRepository.findByUsername(username);
}
//  public User logUp(UsersDTO usersDTO){
//      User newUser = User.builder()
//              .mail(usersDTO.getMail())
//              .username(usersDTO.getUsername())
//              .password(bCryptPasswordEncoder.encode(usersDTO.getPassword()))
//              .authorities(usersDTO.getAuthorities())
//              .accountNonLocked(true)
//              .isEnabled(true)
//              .credentialsNonExpired(true)
//              .accountNonExpired(true)
//          .build();
//      return userRepository.save(newUser);
//
//  }

}

