package id.ac.ui.cs.advprog.authenticationuserprofile.controller;

import id.ac.ui.cs.advprog.authenticationuserprofile.dto.UserProfileDTO;
import id.ac.ui.cs.advprog.authenticationuserprofile.dto.AuthenticationRequest;
import id.ac.ui.cs.advprog.authenticationuserprofile.dto.AuthenticationResponse;
import id.ac.ui.cs.advprog.authenticationuserprofile.dto.RegisterRequest;
import id.ac.ui.cs.advprog.authenticationuserprofile.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetHomePage() {
        ResponseEntity<Object> response = userController.getHomePage();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid endpoint. Try /login or /register.", response.getBody());
    }

    @Test
    void testPostHomePage() {
        ResponseEntity<Object> response = userController.postHomePage();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid endpoint. Try /login or /register.", response.getBody());
    }

    @Test
    void testRegister() {
        RegisterRequest registerRequest = new RegisterRequest("username", "Full Name", "password", "email@example.com", "USER", "1234567890", "Address");
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .status("success")
                .message("User registered successfully.")
                .build();

        when(userService.register(any(RegisterRequest.class))).thenReturn(authenticationResponse);

        ResponseEntity<Object> response = userController.register(registerRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authenticationResponse, response.getBody());
    }

    @Test
    void testRegisterUserAlreadyExists() {
        RegisterRequest registerRequest = new RegisterRequest("username", "Full Name", "password", "email@example.com", "USER", "1234567890", "Address");
        when(userService.register(any(RegisterRequest.class))).thenThrow(new IllegalArgumentException("User already exists!"));

        ResponseEntity<Object> response = userController.register(registerRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User already exists!", ((AuthenticationResponse) response.getBody()).getMessage());
    }

    @Test
    void testAuthenticate() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("username", "password");
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .status("success")
                .message("Authentication successful.")
                .build();

        when(userService.authenticate(any(AuthenticationRequest.class))).thenReturn(authenticationResponse);

        ResponseEntity<Object> response = userController.authenticate(authenticationRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authenticationResponse, response.getBody());
    }

    @Test
    void testAuthenticateFailed() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("username", "password");
        when(userService.authenticate(any(AuthenticationRequest.class))).thenThrow(new RuntimeException("Login failed."));

        ResponseEntity<Object> response = userController.authenticate(authenticationRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Login failed.", ((AuthenticationResponse) response.getBody()).getMessage());
    }

    @Test
    void testGetRegister() {
        ResponseEntity<Object> response = userController.getRegister();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid method.", response.getBody());
    }

    @Test
    void testGetLogin() {
        ResponseEntity<Object> response = userController.getLogin();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid method.", response.getBody());
    }

    @Test
    void testGetAllProfiles() {
        List<UserProfileDTO> profiles = Arrays.asList(
                new UserProfileDTO("username", "Full Name", "email@example.com", "USER", "1234567890", "Address")
        );
        when(userService.getAllProfiles()).thenReturn(profiles);

        ResponseEntity<List<UserProfileDTO>> response = userController.getAllProfiles();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profiles, response.getBody());
    }

    @Test
    void testGetProfile() {
        UserProfileDTO profile = new UserProfileDTO("username", "Full Name", "email@example.com", "USER", "1234567890", "Address");
        when(userService.getProfileByUsername(eq("username"))).thenReturn(profile);

        ResponseEntity<UserProfileDTO> response = userController.getProfile("username");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profile, response.getBody());
    }

    @Test
    void testUpdateProfile() {
        UserProfileDTO profile = new UserProfileDTO("username", "Full Name", "email@example.com", "USER", "1234567890", "Address");
        UserProfileDTO updatedProfile = new UserProfileDTO("username", "Updated Name", "email@example.com", "USER", "1234567890", "Address");

        when(userService.updateProfile(eq("username"), any(UserProfileDTO.class))).thenReturn(updatedProfile);

        ResponseEntity<UserProfileDTO> response = userController.updateProfile("username", profile);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProfile, response.getBody());
    }

    @Test
    void testDeleteProfile() {
        doNothing().when(userService).deleteProfile(eq("username"));

        ResponseEntity<Void> response = userController.deleteProfile("username");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testLogout() {
        ResponseEntity<Object> response = userController.logout();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logout successful.", response.getBody());
    }

    @Test
    void testGetUserRole() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "username");
        when(userService.getUserRole(eq("username"))).thenReturn("USER");

        ResponseEntity<String> response = userController.getUserRole(requestBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("USER", response.getBody());
    }
}
