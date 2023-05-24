package com.example.redditclone.users.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;


class RoleTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        String ability = "ADMIN";
        Set<User> users = new HashSet<>();
        Set<Permission> permissions = new HashSet<>();

        // Act
        Role role = new Role();
        role.setAbility(ability);
        role.setUsers(users);
        role.setPermissions(permissions);

        // Assert
        assertEquals(null, role.getId());
        assertEquals(ability, role.getAbility());
        assertEquals(users, role.getUsers());
        assertEquals(permissions, role.getPermissions());
    }

    @Test
    void testConstructor() {
        // Arrange
        String ability = "MODERATOR";

        // Act
        Role role = new Role(ability);

        // Assert
        assertNull(role.getId());
        assertEquals(ability, role.getAbility());
        assertEquals(null, role.getUsers());
        assertEquals(null, role.getPermissions());
    }

    @Test
    void testDefaultConstructor() {
        // Arrange & Act
        Role role = new Role();

        // Assert
        assertNull(role.getId());
        assertNull(role.getAbility());
        assertEquals(null, role.getUsers());
        assertEquals(null, role.getPermissions());
    }

    @Test
    void testSettersReturnRoleInstance() {
        // Arrange
        Role role = new Role();
        Set<User> users = new HashSet<>();
        Set<Permission> permissions = new HashSet<>();

        // Act
        Role updatedRole = role.setAbility("USER").setUsers(users).setPermissions(permissions);

        // Assert
        assertSame(role, updatedRole);
    }
}
