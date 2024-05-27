package com.example.rest.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
@Getter
@AllArgsConstructor
public enum Role implements GrantedAuthority {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");
    private String value;


    @Override
    public String getAuthority() {
        return name();
    }
}
