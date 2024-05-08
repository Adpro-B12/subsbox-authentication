package id.ac.ui.cs.advprog.authenticationuserprofile.service;

import id.ac.ui.cs.advprog.authenticationuserprofile.model.User;
import id.ac.ui.cs.advprog.authenticationuserprofile.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.CompletableFuture;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        user = new User();
        user.setId(userId);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFullName("Test User");
        user.setPhoneNumber("1234567890");
        user.setAddress("123 Test Ave");
    }

    @Test
    void whenRegisterUser_thenSaveUser() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        CompletableFuture<User> future = userService.registerNewUser(user);
        User registeredUser = future.join();

        assertEquals(userId, registeredUser.getId());
        assertEquals("encodedPassword", registeredUser.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void whenUpdateUser_thenUpdateUserDetails() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        CompletableFuture<User> future = userService.updateUser(user);
        User updatedUser = future.join();

        assertEquals("Updated Test User", updatedUser.getFullName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void whenDeleteUser_thenRepositoryDeleteCalled() {
        doNothing().when(userRepository).deleteById(any(UUID.class));

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void whenGetUserByEmail_thenReturnUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        CompletableFuture<User> future = userService.getUserByEmail("test@example.com");
        User foundUser = future.join();

        assertEquals("test@example.com", foundUser.getEmail());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

}