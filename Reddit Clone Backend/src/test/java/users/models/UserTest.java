package users.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void create_user() {
        User user = new User("testUsername", "test@email.com", "testPassword", false);
        assertEquals("testUsername", user.getUsername());
        assertEquals("test@email.com", user.getEmail());
        assertEquals("testPassword", user.getPassword());
        assertEquals(false, user.isMfa());
    }

    @Test
    void set_new_password() {
        User user = new User("testUsername", "test@email.com", "testPassword", false);
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    void set_new_username() {
        User user = new User("testUsername", "test@email.com", "testPassword", false);
        user.setUsername("testUsername2");
        assertEquals("testUsername2", user.getUsername());
    }

    @Test
    void get_time_created() {
        User user = new User("testUsername", "test@email.com", "testPassword", false);
        assertEquals(LocalDateTime.now(), user.getCreatedAt());
    }
}