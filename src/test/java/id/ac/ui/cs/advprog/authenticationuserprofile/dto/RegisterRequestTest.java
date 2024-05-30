package id.ac.ui.cs.advprog.authenticationuserprofile.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegisterRequestTest {

    @Test
    void testRegisterRequestGettersAndSetters() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("username");
        request.setFullName("Full Name");
        request.setPassword("password");
        request.setEmail("email@example.com");
        request.setRole("USER");
        request.setPhoneNumber("1234567890");
        request.setAddress("Address");

        assertEquals("username", request.getUsername());
        assertEquals("Full Name", request.getFullName());
        assertEquals("password", request.getPassword());
        assertEquals("email@example.com", request.getEmail());
        assertEquals("USER", request.getRole());
        assertEquals("1234567890", request.getPhoneNumber());
        assertEquals("Address", request.getAddress());
    }

    @Test
    void testRegisterRequestBuilder() {
        RegisterRequest request = RegisterRequest.builder()
                .username("username")
                .fullName("Full Name")
                .password("password")
                .email("email@example.com")
                .role("USER")
                .phoneNumber("1234567890")
                .address("Address")
                .build();

        assertEquals("username", request.getUsername());
        assertEquals("Full Name", request.getFullName());
        assertEquals("password", request.getPassword());
        assertEquals("email@example.com", request.getEmail());
        assertEquals("USER", request.getRole());
        assertEquals("1234567890", request.getPhoneNumber());
        assertEquals("Address", request.getAddress());
    }

    @Test
    void testRegisterRequestConstructor() {
        RegisterRequest request = new RegisterRequest(
                "username", "Full Name", "password",
                "email@example.com", "USER", "1234567890", "Address"
        );

        assertEquals("username", request.getUsername());
        assertEquals("Full Name", request.getFullName());
        assertEquals("password", request.getPassword());
        assertEquals("email@example.com", request.getEmail());
        assertEquals("USER", request.getRole());
        assertEquals("1234567890", request.getPhoneNumber());
        assertEquals("Address", request.getAddress());
    }

    @Test
    void testEqualsAndHashCode() {
        RegisterRequest request1 = new RegisterRequest(
                "username", "Full Name", "password",
                "email@example.com", "USER", "1234567890", "Address"
        );
        RegisterRequest request2 = new RegisterRequest(
                "username", "Full Name", "password",
                "email@example.com", "USER", "1234567890", "Address"
        );
        RegisterRequest request3 = new RegisterRequest(
                "differentUsername", "Full Name", "password",
                "email@example.com", "USER", "1234567890", "Address"
        );

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }

    @Test
    void testToString() {
        RegisterRequest request = new RegisterRequest(
                "username", "Full Name", "password",
                "email@example.com", "USER", "1234567890", "Address"
        );
        String expectedToString = "RegisterRequest(username=username, fullName=Full Name, password=password, email=email@example.com, role=USER, phoneNumber=1234567890, address=Address)";

        assertEquals(expectedToString, request.toString());
    }
}
