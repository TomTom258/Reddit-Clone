package com.example.redditclone.security;

import com.example.redditclone.users.models.Role;
import com.example.redditclone.users.models.User;
import com.example.redditclone.users.repositories.RoleRepository;
import com.example.redditclone.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    private LoginAttemptService loginAttemptService;
    private RoleRepository roleRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, LoginAttemptService loginAttemptService,
                                    RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.loginAttemptService = loginAttemptService;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (loginAttemptService.isBlocked()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This address is blocked");
        }

        try {
            User user = userRepository.findByUsername(username);

            if (user == null) {
                return new org.springframework.security.core.userdetails.User(
                        " ", " ", true, true, true, true,
                        mapRolesToAuthorities(Collections.singletonList(roleRepository.findByName("ROLE_USER"))));
            }
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true,
                    mapRolesToAuthorities(user.getRoles()));
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}