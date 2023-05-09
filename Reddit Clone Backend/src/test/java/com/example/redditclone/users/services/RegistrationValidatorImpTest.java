package com.example.redditclone.users.services;

import com.example.redditclone.users.models.User;
import com.example.redditclone.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RegistrationValidatorImpTest {
    @Mock
    private UserRepository fakeRepository;
    @InjectMocks
    private RegistrationValidatorImp fakeValidator;

    @Test
    void validateUsername_returns_true_with_correct_input() {
        User fakeUser = new User("username", "email@email.com", "password", false);

        Boolean result = fakeValidator.validateUsername(fakeUser);
        assertEquals(true, result);
    }

    @Test
    void validateUsername_throws_exception_if_user_exists() {
        User fakeUser = new User("username", "email@email.com", "password", false);

        Mockito.when(fakeRepository.findByUsername(Mockito.any())).thenReturn(fakeUser);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> fakeValidator.validateUsername(fakeUser));

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("User already exists", exception.getReason());
    }

    @Test
    void validateUsername_throws_exception_if_username_is_too_short() {
        User fakeUser = new User("use", "email@email.com", "password", false);

        Mockito.when(fakeRepository.findByUsername(Mockito.any())).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> fakeValidator.validateUsername(fakeUser));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Username must be at least 4 characters long", exception.getReason());
    }

    @Test
    void validatePassword_returns_true_with_correct_input() {
        User fakeUser = new User("username", "email@email.com", "password", false);

        Boolean result = fakeValidator.validatePassword(fakeUser);

        assertEquals(true, result);
    }

    @Test
    void validatePassword_throws_exception_if_password_is_empty() {
        User fakeUser = new User("username", "email@email.com", "", false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> fakeValidator.validatePassword(fakeUser));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Password is required", exception.getReason());
    }

    @Test
    void validatePassword_throws_exception_if_password_is_too_short() {
        User fakeUser = new User("use", "email@email.com", "passwor", false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> fakeValidator.validatePassword(fakeUser));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Password must be at least 8 characters long", exception.getReason());
    }

    @Test
    void validateEmail_returns_true_with_correct_input() {
        User fakeUser = new User("username", "email@email.com", "password", false);

        Mockito.when(fakeRepository.findByEmail(Mockito.any())).thenReturn(null);

        Boolean result = fakeValidator.validateEmail(fakeUser);

        assertEquals(true, result);
    }

    @Test
    void validateEmail_throws_exception_if_email_exists() {
        User fakeUser = new User("username", "email@email.com", "password", false);

        Mockito.when(fakeRepository.findByEmail(Mockito.any())).thenReturn(fakeUser);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> fakeValidator.validateEmail(fakeUser));

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("Email already exists", exception.getReason());
    }

    @Test
    void validateEmail_throws_exception_if_email_is_empty() {
        User fakeUser = new User("username", "", "", false);

        Mockito.when(fakeRepository.findByEmail(Mockito.any())).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> fakeValidator.validateEmail(fakeUser));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Email is required", exception.getReason());
    }

    @Test
    void validateEmail_throws_exception_if_email_is_invalid() {
        User fakeUser = new User("use", "email", "password", false);

        Mockito.when(fakeRepository.findByEmail(Mockito.any())).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> fakeValidator.validateEmail(fakeUser));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Invalid email", exception.getReason());
    }

    @Test
    void registerUser_returns_true_with_correct_user_input() {
        User fakeUser = new User("username", "email@email.com", "password", false);
        Boolean result = fakeValidator.registerUser(fakeUser);

        assertEquals(true, result);
    }
}