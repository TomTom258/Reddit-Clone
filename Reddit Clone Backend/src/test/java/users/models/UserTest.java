package users.models;

import com.example.redditclone.users.models.User;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    @Test
    void verification_token_expires_in_one_hour() {
        User user = new User("testUsername", "test@email.com", "testPassword", false);
        assertEquals(LocalDateTime.now().plusHours(1).truncatedTo(ChronoUnit.MINUTES), user.getVerificationTokenExpiresAt().truncatedTo(ChronoUnit.MINUTES));
    }
}