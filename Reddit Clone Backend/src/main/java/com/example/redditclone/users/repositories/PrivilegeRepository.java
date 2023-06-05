package com.example.redditclone.users.repositories;

import com.example.redditclone.users.models.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}
