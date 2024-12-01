package com.depoisdosim.depoisdosim.domain.user;

public record RegisterDTO(String username, String password, String email, UserRole role) {
    
}
