package com.example.redditclone.dtos;

public class RoleDto {
    private String role;
    private String roleGrantedToUsername;

    public RoleDto() {
    }

    public RoleDto(String role, String roleGrantedToUsername) {
        this.role = role;
        this.roleGrantedToUsername = roleGrantedToUsername;
    }

    public String getRole() {
        return role;
    }

    public String getRoleGrantedToUsername() {
        return roleGrantedToUsername;
    }
}
