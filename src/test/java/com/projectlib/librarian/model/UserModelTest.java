package com.projectlib.librarian.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("User model tests")
public class UserModelTest {

    private UserModel user;

    @BeforeEach
    void setUp() {
        user = new UserModel();
    }

    @Test
    @DisplayName("Get ID")
    public void testGetId() {
        user.setId(1L);
        assertEquals(1L, user.getId());
    }

    @Test
    @DisplayName("Get Name")
    public void testGetName() {
        user.setName("John");
        assertEquals("John", user.getName());
    }

    @Test
    @DisplayName("Get Surname")
    public void testGetSurname() {
        user.setSurname("Doe");
        assertEquals("Doe", user.getSurname());
    }

    @Test
    @DisplayName("Get Email")
    public void testGetEmail() {
        user.setEmail("email@email.com");
        assertEquals("email@email.com", user.getEmail());
    }

    @Test
    @DisplayName("Get Username")
    public void testGetUsername() {
        user.setUsername("username");
        assertEquals("username", user.getUsername());
    }

    @Test
    @DisplayName("Get Password")
    public void testGetPassword() {
        user.setPassword("pass1234");
        assertEquals("pass1234", user.getPassword());
    }

    @Test
    @DisplayName("Get Status")
    public void testGetStatus() {
        user.setStatus(true);
        assertEquals(true, user.getStatus());
    }

    @Test
    @DisplayName("Set ID")
    public void testSetId() {
        user.setId(1L);
        assertEquals(1L, user.getId());
    }

    @Test
    @DisplayName("Set Name")
    public void testSetName() {
        user.setName("John");
        assertEquals("John", user.getName());
    }

    @Test
    @DisplayName("Set Surname")
    public void testSetSurname() {
        user.setSurname("Doe");
        assertEquals("Doe", user.getSurname());
    }

    @Test
    @DisplayName("Set Email")
    public void testSetEmail() {
        user.setEmail("email@email.com");
        assertEquals("email@email.com", user.getEmail());
    }

    @Test
    @DisplayName("Set Username")
    public void testSetUsername() {
        user.setUsername("username");
        assertEquals("username", user.getUsername());
    }

    @Test
    @DisplayName("Set Password")
    public void testSetPassword() {
        user.setPassword("pass1234");
        assertEquals("pass1234", user.getPassword());
    }

    @Test
    @DisplayName("Set Status")
    public void testSetStatus() {
        user.setStatus(true);
        assertEquals(true, user.getStatus());
    }
}
