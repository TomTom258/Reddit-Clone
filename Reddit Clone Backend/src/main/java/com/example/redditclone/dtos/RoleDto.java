package com.example.redditclone.dtos;

public class RoleDto {
    private String role;
    private String toUsername;

    public RoleDto() {
    }

    public RoleDto(String role, String toUsername) {
        this.role = role;
        this.toUsername = toUsername;
    }

    public String getRole() {
        return role;
    }

    public String getToUsername() {
        return toUsername;
    }
}
