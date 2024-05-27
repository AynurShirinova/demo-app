package com.example.rest.dto;

import com.example.rest.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UsersDTO {
    private String id;
    private String username;
    private String mail;
    private String password;
    private Set<Role> authorities;

}
