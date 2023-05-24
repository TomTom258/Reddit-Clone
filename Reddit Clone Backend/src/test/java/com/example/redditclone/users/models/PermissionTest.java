package com.example.redditclone.users.models;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PermissionTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        String ability = "READ_POST";
        Set<User> users = new HashSet<>();
        Set<Role> roles = new HashSet<>();

        // Act
        Permission permission = new Permission();
        permission.setAbility(ability);
        permission.setUsers(users);
        permission.setRoles(roles);

        // Assert
        assertEquals(null, permission.getId());
        assertEquals(ability, permission.getAbility());
        assertEquals(users, permission.getUsers());
        assertEquals(roles, permission.getRoles());
    }

    @Test
    void testConstructor() {
        // Arrange
        String ability = "WRITE_POST";

        // Act
        Permission permission = new Permission(ability);

        // Assert
        assertNull(permission.getId());
        assertEquals(ability, permission.getAbility());
        assertNotNull(permission.getUsers());
        assertNotNull(permission.getRoles());
    }

    @Test
    void testDefaultConstructor() {
        // Arrange & Act
        Permission permission = new Permission();

        // Assert
        assertNull(permission.getId());
        assertNull(permission.getAbility());
        assertNotNull(permission.getUsers());
        assertNotNull(permission.getRoles());
    }
}
