package com.depoisdosim.depoisdosim.domain.user;

public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    SUPPLIER("supplier");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
