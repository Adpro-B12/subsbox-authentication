package id.ac.ui.cs.advprog.authenticationuserprofile.service;

import id.ac.ui.cs.advprog.authenticationuserprofile.dto.UserProfileDTO;
import id.ac.ui.cs.advprog.authenticationuserprofile.dto.AuthenticationRequest;
import id.ac.ui.cs.advprog.authenticationuserprofile.dto.AuthenticationResponse;
import id.ac.ui.cs.advprog.authenticationuserprofile.dto.RegisterRequest;
import id.ac.ui.cs.advprog.authenticationuserprofile.enums.UserRole;
import id.ac.ui.cs.advprog.authenticationuserprofile.model.User;
import id.ac.ui.cs.advprog.authenticationuserprofile.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserServiceImpl userService;

    private AuthenticationRequest loginRequest;
    private RegisterRequest registerRequest;
    private User user;

    @BeforeEach
    void setUp() {
        String username = "nandanathaniela";
        String fullName = "Nanda Nathaniela Meizari";
        String email = "nandanathaniela@meizari.com";
        String password = "nandanathaniela123";
        String phoneNumber = "08123456789";
        String address = "Beji, Depok";

        doReturn("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO").when(passwordEncoder).encode(password);

        String encodedPassword = passwordEncoder.encode(password);

        registerRequest = new RegisterRequest();
        loginRequest = new AuthenticationRequest();

        registerRequest.setUsername(username);
        registerRequest.setFullName(fullName);
        registerRequest.setEmail(email);
        registerRequest.setPassword(encodedPassword);
        registerRequest.setRole(UserRole.USER.getRole());
        registerRequest.setPhoneNumber(phoneNumber);
        registerRequest.setAddress(address);

        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        user = User.builder()
                .username(username)
                .fullName(fullName)
                .password(encodedPassword)
                .email(email)
                .role(UserRole.USER.getRole())
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
    }

    /*
    Register tests
     */
    @Test
    void testValidRegister() {
        doReturn("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO")
                .when(passwordEncoder)
                .encode(registerRequest.getPassword());

        AuthenticationResponse registerResponse = userService.register(registerRequest);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testValidRegisterEmptyFullName() {
        registerRequest.setFullName("");

        doReturn("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO")
                .when(passwordEncoder)
                .encode(registerRequest.getPassword());

        AuthenticationResponse registerResponse = userService.register(registerRequest);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testInvalidRegisterEmptyUsername() {
        registerRequest.setUsername("");
        assertThrows(IllegalArgumentException.class, () -> userService.register(registerRequest));
    }

    @Test
    void testInvalidRegisterEmptyPassword() {
        registerRequest.setPassword("");
        assertThrows(IllegalArgumentException.class, () -> userService.register(registerRequest));
    }

    @Test
    void testInvalidRegisterEmptyEmail() {
        registerRequest.setEmail("");
        doReturn("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO")
                .when(passwordEncoder)
                .encode(registerRequest.getPassword());
        assertThrows(IllegalArgumentException.class, () -> userService.register(registerRequest));
    }

    @Test
    void testInvalidRegisterEmptyRole() {
        registerRequest.setRole("");
        doReturn("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO")
                .when(passwordEncoder)
                .encode(registerRequest.getPassword());
        assertThrows(IllegalArgumentException.class, () -> userService.register(registerRequest));
    }

    @Test
    void testInvalidRegisterEmptyPhoneNumber() {
        registerRequest.setRole("");
        doReturn("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO")
                .when(passwordEncoder)
                .encode(registerRequest.getPassword());
        assertThrows(IllegalArgumentException.class, () -> userService.register(registerRequest));
    }

    @Test
    void testInvalidRegisterEmptyAddress() {
        registerRequest.setRole("");
        doReturn("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO")
                .when(passwordEncoder)
                .encode(registerRequest.getPassword());
        assertThrows(IllegalArgumentException.class, () -> userService.register(registerRequest));
    }

    /*
    Login tests
     */
    @Test
    void testValidLogin() {
        String token = "abcde.fghij.klmno";
        User user = User.builder().username(loginRequest.getUsername()).password(loginRequest.getPassword()).build();
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole());
        doReturn(token).when(jwtService).generateToken(extraClaims, user);
        doReturn(Optional.of(user)).when(userRepository).findByUsername(loginRequest.getUsername());
        doReturn(null).when(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));

        AuthenticationResponse loginResponse = userService.authenticate(loginRequest);
        verify(userRepository, times(1)).findByUsername("nandanathaniela");
        assertEquals(loginResponse.getToken(), token);
    }

    @Test
    void testInvalidLogin() {
        loginRequest.setPassword("passwordSalah");
        assertThrows(NoSuchElementException.class, () -> userService.authenticate(loginRequest));
    }

    /*
    User Profile tests
     */
    @Test
    void testGetAllProfiles() {
        List<User> users = List.of(user);
        doReturn(users).when(userRepository).findAll();

        List<UserProfileDTO> profiles = userService.getAllProfiles();
        assertEquals(1, profiles.size());
        assertEquals(user.getUsername(), profiles.get(0).getUsername());
    }

    @Test
    void testGetProfileByUsername() {
        doReturn(Optional.of(user)).when(userRepository).findByUsername(user.getUsername());

        UserProfileDTO profile = userService.getProfileByUsername(user.getUsername());
        assertEquals(user.getUsername(), profile.getUsername());
    }

    @Test
    void testGetProfileByUsernameNotFound() {
        doReturn(Optional.empty()).when(userRepository).findByUsername(user.getUsername());

        assertThrows(IllegalArgumentException.class, () -> userService.getProfileByUsername(user.getUsername()));
    }

    @Test
    void testUpdateProfile() {
        doReturn(Optional.of(user)).when(userRepository).findByUsername(user.getUsername());

        UserProfileDTO updatedProfile = new UserProfileDTO(user.getUsername(), "Updated Name", user.getEmail(), user.getRole().name(), user.getPhoneNumber(), user.getAddress());
        UserProfileDTO result = userService.updateProfile(user.getUsername(), updatedProfile);

        assertEquals("Updated Name", result.getFullName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateProfileNotFound() {
        doReturn(Optional.empty()).when(userRepository).findByUsername(user.getUsername());

        UserProfileDTO updatedProfile = new UserProfileDTO(user.getUsername(), "Updated Name", user.getEmail(), user.getRole().name(), user.getPhoneNumber(), user.getAddress());
        assertThrows(IllegalArgumentException.class, () -> userService.updateProfile(user.getUsername(), updatedProfile));
    }

    @Test
    void testDeleteProfile() {
        doReturn(Optional.of(user)).when(userRepository).findByUsername(user.getUsername());

        userService.deleteProfile(user.getUsername());
        verify(userRepository, times(1)).delete(any(User.class));
    }

    @Test
    void testDeleteProfileNotFound() {
        doReturn(Optional.empty()).when(userRepository).findByUsername(user.getUsername());

        assertThrows(IllegalArgumentException.class, () -> userService.deleteProfile(user.getUsername()));
    }
}