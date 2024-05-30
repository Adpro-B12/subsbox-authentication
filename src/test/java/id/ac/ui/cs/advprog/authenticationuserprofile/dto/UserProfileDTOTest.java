package id.ac.ui.cs.advprog.authenticationuserprofile.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserProfileDTOTest {

    @Test
    void testUserProfileDTOGettersAndSetters() {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUsername("username");
        userProfileDTO.setFullName("Full Name");
        userProfileDTO.setEmail("email@example.com");
        userProfileDTO.setRole("USER");
        userProfileDTO.setPhoneNumber("1234567890");
        userProfileDTO.setAddress("Address");

        assertEquals("username", userProfileDTO.getUsername());
        assertEquals("Full Name", userProfileDTO.getFullName());
        assertEquals("email@example.com", userProfileDTO.getEmail());
        assertEquals("USER", userProfileDTO.getRole());
        assertEquals("1234567890", userProfileDTO.getPhoneNumber());
        assertEquals("Address", userProfileDTO.getAddress());
    }

    @Test
    void testUserProfileDTOBuilder() {
        UserProfileDTO userProfileDTO = UserProfileDTO.builder()
                .username("username")
                .fullName("Full Name")
                .email("email@example.com")
                .role("USER")
                .phoneNumber("1234567890")
                .address("Address")
                .build();

        assertEquals("username", userProfileDTO.getUsername());
        assertEquals("Full Name", userProfileDTO.getFullName());
        assertEquals("email@example.com", userProfileDTO.getEmail());
        assertEquals("USER", userProfileDTO.getRole());
        assertEquals("1234567890", userProfileDTO.getPhoneNumber());
        assertEquals("Address", userProfileDTO.getAddress());
    }

    @Test
    void testUserProfileDTOConstructor() {
        UserProfileDTO userProfileDTO = new UserProfileDTO(
                "username", "Full Name", "email@example.com",
                "USER", "1234567890", "Address"
        );

        assertEquals("username", userProfileDTO.getUsername());
        assertEquals("Full Name", userProfileDTO.getFullName());
        assertEquals("email@example.com", userProfileDTO.getEmail());
        assertEquals("USER", userProfileDTO.getRole());
        assertEquals("1234567890", userProfileDTO.getPhoneNumber());
        assertEquals("Address", userProfileDTO.getAddress());
    }

    @Test
    void testEqualsAndHashCode() {
        UserProfileDTO userProfileDTO1 = new UserProfileDTO(
                "username", "Full Name", "email@example.com",
                "USER", "1234567890", "Address"
        );
        UserProfileDTO userProfileDTO2 = new UserProfileDTO(
                "username", "Full Name", "email@example.com",
                "USER", "1234567890", "Address"
        );
        UserProfileDTO userProfileDTO3 = new UserProfileDTO(
                "differentUsername", "Full Name", "email@example.com",
                "USER", "1234567890", "Address"
        );

        assertEquals(userProfileDTO1, userProfileDTO2);
        assertNotEquals(userProfileDTO1, userProfileDTO3);
        assertEquals(userProfileDTO1.hashCode(), userProfileDTO2.hashCode());
        assertNotEquals(userProfileDTO1.hashCode(), userProfileDTO3.hashCode());
    }

    @Test
    void testToString() {
        UserProfileDTO userProfileDTO = new UserProfileDTO(
                "username", "Full Name", "email@example.com",
                "USER", "1234567890", "Address"
        );
        String expectedToString = "UserProfileDTO(username=username, fullName=Full Name, email=email@example.com, role=USER, phoneNumber=1234567890, address=Address)";

        assertEquals(expectedToString, userProfileDTO.toString());
    }
}
