package id.ac.ui.cs.advprog.authenticationuserprofile.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    User user;
    @Test
    void testGetUserID() {
        this.user = new User();
        this.user.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        assertEquals(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"), this.user.getId());
    }

    @Test
    void testGetEmail() {
        this.user = new User();
        this.user.setEmail("test@email.com");
        assertEquals("test@email.com", this.user.getEmail());
    }

    @Test
    void testGetPassword() {
        this.user = new User();
        this.user.setPassword("password");
        assertEquals("password", this.user.getPassword());
    }

    @Test
    void testGetFullName() {
        this.user = new User();
        this.user.setFullName("fullname");
        assertEquals("fullname", this.user.getFullName());
    }

    @Test
    void testGetPhoneNumber() {
        this.user = new User();
        this.user.setPhoneNumber("0812345678");
        assertEquals("0812345678", this.user.getPhoneNumber());
    }

    @Test
    void testGetAddress() {
        this.user = new User();
        this.user.setAddress("Margonda");
        assertEquals("Margonda", this.user.getAddress());
    }
}