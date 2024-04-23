package id.ac.ui.cs.advprog.authenticationuserprofile.service;

import id.ac.ui.cs.advprog.authenticationuserprofile.model.User;
import id.ac.ui.cs.advprog.authenticationuserprofile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + user.getId()));
        existingUser.setFullName(user.getFullName());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setAddress(user.getAddress());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}