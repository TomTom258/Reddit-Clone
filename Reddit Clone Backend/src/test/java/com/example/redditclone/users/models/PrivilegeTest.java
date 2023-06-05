package com.example.redditclone.users.models;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PrivilegeTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        String ability = "READ_POST";
        Set<User> users = new HashSet<>();
        Set<Role> roles = new HashSet<>();

        // Act
        Privilege privilege = new Privilege();
        privilege.setAbility(ability);
        privilege.setUsers(users);
        privilege.setRoles(roles);

        // Assert
        assertEquals(null, privilege.getId());
        assertEquals(ability, privilege.getAbility());
        assertEquals(users, privilege.getUsers());
        assertEquals(roles, privilege.getRoles());
    }

    @Test
    void testConstructor() {
        // Arrange
        String ability = "WRITE_POST";

        // Act
        Privilege privilege = new Privilege(ability);

        // Assert
        assertNull(privilege.getId());
        assertEquals(ability, privilege.getAbility());
        assertNotNull(privilege.getUsers());
        assertNotNull(privilege.getRoles());
    }

    @Test
    void testDefaultConstructor() {
        // Arrange & Act
        Privilege privilege = new Privilege();

        // Assert
        assertNull(privilege.getId());
        assertNull(privilege.getAbility());
        assertNotNull(privilege.getUsers());
        assertNotNull(privilege.getRoles());
    }
}
