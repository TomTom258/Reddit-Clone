package com.example.redditclone.users.repositories;

import com.example.redditclone.users.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
