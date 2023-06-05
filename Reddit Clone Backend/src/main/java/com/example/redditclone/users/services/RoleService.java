package com.example.redditclone.users.services;

import com.example.redditclone.users.models.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    boolean grantRoleToUser(String role, String username);
    boolean stripRoleFromUser(String role, String username);
}
