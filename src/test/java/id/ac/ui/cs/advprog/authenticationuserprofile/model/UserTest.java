package id.ac.ui.cs.advprog.authenticationuserprofile.model;

import id.ac.ui.cs.advprog.authenticationuserprofile.enums.UserRole;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    UserBuilder userBuilder;
    @BeforeEach
    void setUp() {
        this.userBuilder = User.builder();
    }
    @Test
    void testCreateUserValidUsername() {
        User user = userBuilder.username("nandanathaniela").build();
        assertEquals("nandanathaniela", user.getUsername());
    }

    @Test
    void testCreateUserInvalidUsername() {
        assertThrows(IllegalArgumentException.class, () -> userBuilder.username("").build());
        assertThrows(IllegalArgumentException.class, () -> userBuilder.username("~@#$!@%@#$%!$^!#$%").build());
    }

    @Test
    void testCreateUserValidPassword() {
        User user = userBuilder.password("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO").build();
        assertEquals("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO",
                user.getPassword());
    }

    @Test
    void testCreateUserInvalidPassword() {
        assertThrows(IllegalArgumentException.class, () -> userBuilder.password("").build());
    }

    @Test
    void testCreateUserFullName() {
        User user = userBuilder.fullName("Nanda Nathaniela").build();
        assertEquals("Nanda Nathaniela",
                user.getFullName());
    }

    @Test
    void testCreateUserDefaultFullName() {
        User user = userBuilder.fullName("").build();
        assertEquals("Pengguna", user.getFullName());
    }

    @Test
    void testCreateUserValidEmail() {
        User user = userBuilder.email("nandanathaniela@gmail.com").build();
        assertEquals("nandanathaniela@gmail.com", user.getEmail());
    }

    @Test
    void testCreateUserInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> userBuilder.email("Does have email.").build());
    }

    @Test
    void testCreateUserValidPhoneNumber() {
        User user = userBuilder.phoneNumber("08123456789").build();
        assertEquals("08123456789", user.getPhoneNumber());
    }

    @Test
    void testCreateUserInvalidPhoneNumber() {
        assertThrows(IllegalArgumentException.class, () -> userBuilder.phoneNumber("").build());
    }

    @Test
    void testCreateUserValidRoles() {
        User user1 = userBuilder.role(UserRole.USER.getRole()).build();
        User user2 = userBuilder.role(UserRole.ADMIN.getRole()).build();

        assertEquals(UserRole.USER, user1.getRole());
        assertEquals(UserRole.ADMIN, user2.getRole());
    }

    @Test
    void testCreateUserNonexistentRole() {
        assertThrows(IllegalArgumentException.class, () -> userBuilder.role("HACKER").build());
    }

    @Test
    void testCreateUserValidAddress() {
        User user = userBuilder.address("Beji, Depok").build();
        assertEquals("Beji, Depok", user.getAddress());
    }

    @Test
    void testCreateUserInvalidAddress() {
        assertThrows(IllegalArgumentException.class, () -> userBuilder.address("").build());
    }
}