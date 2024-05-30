package id.ac.ui.cs.advprog.authenticationuserprofile.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthenticationRequestTest {

    @Test
    void testAuthenticationRequestGettersAndSetters() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("username");
        request.setPassword("password");

        assertEquals("username", request.getUsername());
        assertEquals("password", request.getPassword());
    }

    @Test
    void testAuthenticationRequestBuilder() {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .username("username")
                .password("password")
                .build();

        assertEquals("username", request.getUsername());
        assertEquals("password", request.getPassword());
    }

    @Test
    void testAuthenticationRequestConstructor() {
        AuthenticationRequest request = new AuthenticationRequest(
                "username", "password"
        );

        assertEquals("username", request.getUsername());
        assertEquals("password", request.getPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        AuthenticationRequest request1 = new AuthenticationRequest(
                "username", "password"
        );
        AuthenticationRequest request2 = new AuthenticationRequest(
                "username", "password"
        );
        AuthenticationRequest request3 = new AuthenticationRequest(
                "differentUsername", "password"
        );

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }

    @Test
    void testToString() {
        AuthenticationRequest request = new AuthenticationRequest(
                "username", "password"
        );
        String expectedToString = "AuthenticationRequest(username=username, password=password)";

        assertEquals(expectedToString, request.toString());
    }
}
