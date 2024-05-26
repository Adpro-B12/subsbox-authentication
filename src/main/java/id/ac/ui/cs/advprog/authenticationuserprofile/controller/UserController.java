package id.ac.ui.cs.advprog.authenticationuserprofile.controller;

import id.ac.ui.cs.advprog.authenticationuserprofile.dto.UserProfileDTO;
import id.ac.ui.cs.advprog.authenticationuserprofile.dto.AuthenticationRequest;
import id.ac.ui.cs.advprog.authenticationuserprofile.dto.AuthenticationResponse;
import id.ac.ui.cs.advprog.authenticationuserprofile.dto.RegisterRequest;
import id.ac.ui.cs.advprog.authenticationuserprofile.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    @GetMapping(value={"", "/"})
    public ResponseEntity<Object> getHomePage() {
        return new ResponseEntity<>("Invalid endpoint. Try /login or /register.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value={"", "/"})
    public ResponseEntity<Object> postHomePage() {
        return new ResponseEntity<>("Invalid endpoint. Try /login or /register.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        ResponseEntity<Object> response;
        try {
            response = new ResponseEntity<>(userService.register(request), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            response = new ResponseEntity<>(AuthenticationResponse.builder()
                    .status("failed")
                    .message(e.getMessage())
                    .build(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping(value={"/register/", "/register"})
    public ResponseEntity<Object> getRegister() {
        return new ResponseEntity<>("Invalid method.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest request) {
        ResponseEntity<Object> response;
        try {
            response = new ResponseEntity<>(userService.authenticate(request), HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(AuthenticationResponse.builder()
                    .status("failed")
                    .message("Login failed.")
                    .build(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping(value={"/login/", "/login"})
    public ResponseEntity<Object> getLogin() {
        return new ResponseEntity<>("Invalid method.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/profiles")
    public ResponseEntity<List<UserProfileDTO>> getAllProfiles() {
        List<UserProfileDTO> profiles = userService.getAllProfiles();
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    @GetMapping("/profiles/me")
    public ResponseEntity<UserProfileDTO> getCurrentUserProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserProfileDTO profile = userService.getProfileByUsername(username);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping("/profiles/{username}")
    public ResponseEntity<UserProfileDTO> getProfile(@PathVariable String username) {
        UserProfileDTO profile = userService.getProfileByUsername(username);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PutMapping("/profiles/{username}")
    public ResponseEntity<UserProfileDTO> updateProfile(@PathVariable String username, @RequestBody UserProfileDTO profile) {
        UserProfileDTO updatedProfile = userService.updateProfile(username, profile);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    @DeleteMapping("/profiles/{username}")
    public ResponseEntity<Void> deleteProfile(@PathVariable String username) {
        userService.deleteProfile(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>("Logout successful.", HttpStatus.OK);

    }

    @GetMapping("/getrole")
    public ResponseEntity<String> getUserRole(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String role = userService.getUserRole(username);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
