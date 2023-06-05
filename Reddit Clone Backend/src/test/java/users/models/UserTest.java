package users.models;

import com.example.redditclone.users.models.Privilege;
import com.example.redditclone.users.models.Role;
import com.example.redditclone.users.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Mock
    private Privilege privilege1;

    @Mock
    private Privilege privilege2;

    @Mock
    private Role role1;

    @Mock
    private Role role2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGettersAndSetters() {
        User user = new User();

        String username = "john.doe";
        String email = "john.doe@example.com";
        String password = "password";
        LocalDateTime verifiedAt = LocalDateTime.now();
        String verificationToken = "verification-token";
        LocalDateTime verificationTokenExpiresAt = LocalDateTime.now().plusHours(1);
        LocalDateTime createdAt = LocalDateTime.now();
        boolean mfa = true;
        String secret = "secret";
        Long karma = 100L;
        String profilePictureFilePath = "/path/to/profile-picture.jpg";
        String forgottenPasswordToken = "forgotten-password-token";
        LocalDateTime forgottenPasswordExpiresAt = LocalDateTime.now().plusDays(1);
        String resetPasswordToken = "reset-password-token";

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setVerifiedAt(verifiedAt);
        user.setVerificationToken(verificationToken);
        user.setVerificationTokenExpiresAt(verificationTokenExpiresAt);
        user.setMfa(mfa);
        user.setSecret(secret);
        user.setKarma(karma);
        user.setProfilePictureFilePath(profilePictureFilePath);
        user.setForgottenPasswordToken(forgottenPasswordToken);
        user.setForgottenPasswordExpiresAt(forgottenPasswordExpiresAt);
        user.setResetPasswordToken(resetPasswordToken);

        assertEquals(null, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(verifiedAt, user.getVerifiedAt());
        assertEquals(verificationToken, user.getVerificationToken());
        assertEquals(verificationTokenExpiresAt, user.getVerificationTokenExpiresAt());
        assertEquals(createdAt, user.getCreatedAt());
        assertTrue(user.isMfa());
        assertEquals(secret, user.getSecret());
        assertEquals(karma, user.getKarma());
        assertEquals(profilePictureFilePath, user.getProfilePictureFilePath());
        assertEquals(forgottenPasswordToken, user.getForgottenPasswordToken());
        assertEquals(forgottenPasswordExpiresAt, user.getForgottenPasswordExpiresAt());
        assertEquals(resetPasswordToken, user.getResetPasswordToken());
    }

    @Test
    void testGetPermissions() {
        User user = new User();
        Set<Privilege> privileges = new HashSet<>();
        privileges.add(privilege1);
        privileges.add(privilege2);

        user.setPermissions(privileges);

        assertEquals(2, user.getPermissions().size());
        assertTrue(user.getPermissions().contains(privilege1));
        assertTrue(user.getPermissions().contains(privilege2));
    }

    @Test
    void testGetRoles() {
        User user = new User();
        Set<Role> roles = new HashSet<>();
        roles.add(role1);
        roles.add(role2);

        user.setRoles(roles);

        assertEquals(2, user.getRoles().size());
        assertTrue(user.getRoles().contains(role1));
        assertTrue(user.getRoles().contains(role2));
    }

    @Test
    void testIsMfa() {
        User user = new User();
        assertFalse(user.isMfa());

        user.setMfa(true);
        assertTrue(user.isMfa());

        user.setMfa(false);
        assertFalse(user.isMfa());
    }

    @Test
    void testGetKarma() {
        User user = new User();
        assertNull(user.getKarma());

        Long karma = 100L;
        user.setKarma(karma);
        assertEquals(karma, user.getKarma());
    }

    @Test
    void testGetProfilePictureFilePath() {
        User user = new User();
        assertNull(user.getProfilePictureFilePath());

        String filePath = "/path/to/profile-picture.jpg";
        user.setProfilePictureFilePath(filePath);
        assertEquals(filePath, user.getProfilePictureFilePath());
    }

    @Test
    void testGetForgottenPasswordToken() {
        User user = new User();
        assertNull(user.getForgottenPasswordToken());

        String token = "forgotten-password-token";
        user.setForgottenPasswordToken(token);
        assertEquals(token, user.getForgottenPasswordToken());
    }

    @Test
    void testGetForgottenPasswordExpiresAt() {
        User user = new User();
        assertNull(user.getForgottenPasswordExpiresAt());

        LocalDateTime expiresAt = LocalDateTime.now().plusDays(1);
        user.setForgottenPasswordExpiresAt(expiresAt);
        assertEquals(expiresAt, user.getForgottenPasswordExpiresAt());
    }

    @Test
    void testGetResetPasswordToken() {
        User user = new User();
        assertNull(user.getResetPasswordToken());

        String token = "reset-password-token";
        user.setResetPasswordToken(token);
        assertEquals(token, user.getResetPasswordToken());
    }
}
