package com.example.redditclone.users.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {
    Role role;

    @BeforeEach
    void init() {
        role = new Role("Test Ability");
    }

    @Test
    void id_returns_null_if_instanciated() {
        assertNull(role.getId());
    }

    @Test
    void getAbility_returns_correct_value() {
        assertEquals("Test Ability", role.getAbility());
    }

    @Test
    void getUsers_returns_null_if_instanciated() {
        assertEquals(null, role.getUsers());
    }

    @Test
    void getPermissions_returns_null_if_instanciated() {
        assertEquals(null, role.getPermissions());
    }
}