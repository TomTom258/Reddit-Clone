package com.example.redditclone.users.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermissionTest {

    Permission permission;
    @BeforeEach
    void init() {
        permission = new Permission("Test Ability");
    }

    @Test
    void id_returns_null_if_instanciated() {
        assertNull(permission.getId());
    }

    @Test
    void getAbility_returns_correct_value() {
        assertEquals("Test Ability", permission.getAbility());
    }

    @Test
    void getUsers_returns_null_if_instanciated() {
        assertEquals(0, permission.getUsers().size());
    }

    @Test
    void getRoles_returns_null_if_instanciated() {
        assertEquals(0, permission.getRoles().size());
    }
}