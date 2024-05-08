package id.ac.ui.cs.advprog.authenticationuserprofile.service;

import id.ac.ui.cs.advprog.authenticationuserprofile.model.User;
import id.ac.ui.cs.advprog.authenticationuserprofile.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationuserprofile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Async
    public CompletableFuture<User> registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return CompletableFuture.completedFuture(userRepository.save(user));
    }

    @Override
    @Async
    public CompletableFuture<User> updateUser(User user) {
        return userRepository.findById(user.getId()).map(u -> {
            u.setFullName(user.getFullName());
            u.setPhoneNumber(user.getPhoneNumber());
            u.setAddress(user.getAddress());
            return CompletableFuture.completedFuture(userRepository.save(u));
        }).orElseGet(() -> CompletableFuture.completedFuture(null));
    }

    @Override
    @Async
    public CompletableFuture<Void> deleteUser(UUID userId) {
        userRepository.deleteById(userId);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    @Async
    public CompletableFuture<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(CompletableFuture::completedFuture)
                .orElseGet(() -> CompletableFuture.completedFuture(null));
    }
}