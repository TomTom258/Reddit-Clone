package com.example.redditclone.users.repositories;

import com.example.redditclone.users.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface RoleRepository extends JpaRepository<Role, Long> {
}
