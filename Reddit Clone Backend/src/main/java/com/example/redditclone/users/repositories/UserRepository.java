package com.example.redditclone.users.repositories;
import com.example.redditclone.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    Boolean existsByUsername(String username);
    User findByVerificationToken(String token);
    Boolean existsByProfilePictureFilePath(String profilePictureFilePath);
    User findByResetPasswordToken(String token);
}
