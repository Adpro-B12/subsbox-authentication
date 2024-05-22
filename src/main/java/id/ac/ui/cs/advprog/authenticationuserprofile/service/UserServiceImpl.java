package id.ac.ui.cs.advprog.authenticationuserprofile.service;

import id.ac.ui.cs.advprog.authenticationuserprofile.dto.UserProfileDTO;
import id.ac.ui.cs.advprog.authenticationuserprofile.dto.AuthenticationRequest;
import id.ac.ui.cs.advprog.authenticationuserprofile.dto.AuthenticationResponse;
import id.ac.ui.cs.advprog.authenticationuserprofile.dto.RegisterRequest;
import id.ac.ui.cs.advprog.authenticationuserprofile.model.User;
import id.ac.ui.cs.advprog.authenticationuserprofile.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists!");
        }
        else if (request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        User user = User.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .role(request.getRole())
                .build();
        userRepository.save(user);
        return AuthenticationResponse.builder()
                .status("success")
                .message("User registered successfully.")
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole());
        String jwtToken = jwtService.generateToken(extraClaims, user);
        Map<String, String> data = new HashMap<String, String>();
        data.put("token", jwtToken);
        return AuthenticationResponse.builder()
                .status("success")
                .message("Authentication successful.")
                .data(data).build();
    }

    @Override
    public List<UserProfileDTO> getAllProfiles() {
        return userRepository.findAll().stream()
                .map(user -> new UserProfileDTO(user.getUsername(), user.getFullName(), user.getEmail(), user.getRole().name(), user.getPhoneNumber(), user.getAddress()))
                .collect(Collectors.toList());
    }

    @Override
    public UserProfileDTO getProfileByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new UserProfileDTO(user.getUsername(), user.getFullName(), user.getEmail(), user.getRole().name(), user.getPhoneNumber(), user.getAddress());
    }

    @Override
    public UserProfileDTO updateProfile(String username, UserProfileDTO profile) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setFullName(profile.getFullName());
        user.setEmail(profile.getEmail());
        user.setPhoneNumber(profile.getPhoneNumber());
        user.setAddress(profile.getAddress());
        userRepository.save(user);
        return new UserProfileDTO(user.getUsername(), user.getFullName(), user.getEmail(), user.getRole().name(), user.getPhoneNumber(), user.getAddress());
    }

    @Override
    public void deleteProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        userRepository.delete(user);
    }
}