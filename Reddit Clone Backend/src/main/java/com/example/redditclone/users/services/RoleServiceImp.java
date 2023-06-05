package com.example.redditclone.users.services;

import com.example.redditclone.users.models.Role;
import com.example.redditclone.users.models.User;
import com.example.redditclone.users.repositories.RoleRepository;
import com.example.redditclone.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImp implements RoleService{
    private RoleRepository roleRepository;
    private UserRepository userRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean grantRoleToUser(String role, String username) {
        if (!roleRepository.existsByName(role)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role doesn't exists!");
        }

        if (!userRepository.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exists!");
        }

        Role grantedRole = roleRepository.findByName(role);
        User user = userRepository.findByUsername(username);
        Set<Role> newRoles = new HashSet<>(user.getRoles());

        newRoles.add(grantedRole);
        user.setRoles(newRoles);
        userRepository.save(user);
        return true;
    }
}
