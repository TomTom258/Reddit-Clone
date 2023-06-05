package com.example.redditclone.config;

import com.example.redditclone.users.models.Privilege;
import com.example.redditclone.users.models.Role;
import com.example.redditclone.users.models.User;
import com.example.redditclone.users.repositories.PrivilegeRepository;
import com.example.redditclone.users.repositories.RoleRepository;
import com.example.redditclone.users.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PrivilegeRepository privilegeRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SetupDataLoader(UserRepository userRepository, RoleRepository roleRepository, PrivilegeRepository privilegeRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        Privilege editPrivilege = createPrivilegeIfNotFound("EDIT_PRIVILEGE");
        Privilege deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, editPrivilege, deletePrivilege);
        List<Privilege> moderatorPrivileges = Arrays.asList(readPrivilege, writePrivilege, editPrivilege);

        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_MODERATOR", moderatorPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        String adminUsername = System.getenv("ADMIN_USERNAME");
        String adminPassword = System.getenv("ADMIN_PASSWORD");

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User admin = new User(adminUsername, "email@test.com", passwordEncoder.encode(adminPassword), false);
        admin.setVerifiedAt(LocalDateTime.now());
        admin.setRoles(Collections.singletonList(adminRole));
        userRepository.save(admin);

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, List<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
